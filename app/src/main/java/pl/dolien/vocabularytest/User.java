package pl.dolien.vocabularytest;

public class User {
    public String userName;
    public String userProfile;
    public int userBestScore;

    public User(){

    }

    public User(String userName, String userProfile, int userBestScore) {
        this.userName = userName;
        this.userProfile = userProfile;
        this.userBestScore = userBestScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public int getUserBestScore() {
        return userBestScore;
    }

    public void setUserBestScore(int userBestScore) {
        this.userBestScore = userBestScore;
    }
}
