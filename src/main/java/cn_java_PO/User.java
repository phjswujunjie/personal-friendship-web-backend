package cn_java_PO;

import javax.validation.constraints.Pattern;

public class User {
    private Long id;

    @Pattern(regexp = "^[1-9]\\d{9}$", message = "账号格式不正确")
    private String account;

    @Pattern(regexp = "^[a-zA-Z\\d^*!_]{8,15}$", message = "密码格式不正确")
    private String password;

    private String nickname;

    private String gender;

    private String avatar;

    private String phone;

    private String birthday;

    private String email;

    private String introduction;

    private String address;

    private String friends;

    private String isSuperuser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends == null ? null : friends.trim();
    }

    public String getIsSuperuser() {
        return isSuperuser;
    }

    public void setIsSuperuser(String isSuperuser) {
        this.isSuperuser = isSuperuser == null ? null : isSuperuser.trim();
    }
}