package leader.domain;

public class TBAuthority {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorities.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorities.username
     *
     * @mbg.generated
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorities.authority
     *
     * @mbg.generated
     */
    private String authority;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorities.id
     *
     * @return the value of authorities.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorities.id
     *
     * @param id the value for authorities.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorities.username
     *
     * @return the value of authorities.username
     *
     * @mbg.generated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorities.username
     *
     * @param username the value for authorities.username
     *
     * @mbg.generated
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorities.authority
     *
     * @return the value of authorities.authority
     *
     * @mbg.generated
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorities.authority
     *
     * @param authority the value for authorities.authority
     *
     * @mbg.generated
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}