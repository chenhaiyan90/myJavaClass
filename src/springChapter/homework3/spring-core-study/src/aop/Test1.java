package aop;

import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Hello world!
 * 
 */
public class Test1 {
	public static void main(String[] args) throws Exception {
		Test1 ae = (Test1) new AopClassLoader(Thread.currentThread().getContextClassLoader()).loadClass(Test1.class.getName() + "_Tmp").newInstance();
		System.err.println(ae.getClass().getName());
		ae.helloAop();
	}

	public void helloAop() {
		System.err.println("helloApp");
	}

	static class AopClassLoader extends ClassLoader implements Opcodes {
		public AopClassLoader(ClassLoader parent) {
			super(parent);
		}

		public Class<?> loadClass(String name) throws ClassNotFoundException {
			if (!name.contains("Test1_Tmp"))
				return super.loadClass(name);
			try {
				ClassWriter cw = new ClassWriter(0);
				//
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(Test1.class.getName().replace(".", "/") + ".class");
				ClassReader reader = new ClassReader(is);
				reader.accept(new AopClassAdapter(ASM4, cw), ClassReader.SKIP_DEBUG);
				//
				byte[] code = cw.toByteArray();
				// FileOutputStream fos = new
				// FileOutputStream("asm_aop.class");
				// fos.write(code);
				// fos.flush();
				// fos.close();
				return this.defineClass(name, code, 0, code.length);
			} catch (Throwable e) {
				e.printStackTrace();
				throw new ClassNotFoundException();
			}
		}
	}

	static class AopClassAdapter extends ClassVisitor implements Opcodes {
		public AopClassAdapter(int api, ClassVisitor cv) {
			super(api, cv);
		}

		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			// 更改类名，并使新类继承原有的类。
			super.visit(version, access, name + "_Tmp", signature, name, interfaces);
			{
				MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
				mv.visitCode();
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESPECIAL, name, "<init>", "()V",false);
				mv.visitInsn(RETURN);
				mv.visitMaxs(1, 1);
				mv.visitEnd();
			}
		}

		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if ("<init>".equals(name))
				return null;
			if (!name.equals("helloAop"))
				return null;
			//
			MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
			return new AopMethod(this.api, mv);
		}
	}

	static class AopMethod extends MethodVisitor implements Opcodes {
		public AopMethod(int api, MethodVisitor mv) {
			super(api, mv);
		}

		@Override
		public void visitCode() {
			super.visitCode();
			this.visitMethodInsn(INVOKESTATIC, "aop/AopInterceptor", "beforeInvoke", "()V",false);
		}

		@Override
		public void visitInsn(int opcode) {
			if (opcode == RETURN) {
				mv.visitMethodInsn(INVOKESTATIC, "aop/AopInterceptor", "afterInvoke", "()V",false);
			}
			super.visitInsn(opcode);
		}
	}
}