package pl.dolien.vocabularytest;

public class User {
    public String userName;
    public String userProfile;
    public int userBestScore_all_topics;
    public int userBestScore_eighth_grade;
    public int userBestScore_human;
    public int userBestScore_home;
    public int userBestScore_education;
    public int userBestScore_job;
    public int userBestScore_private_life;
    public int userBestScore_nutrition;
    public int userBestScore_shopping_and_services;
    public int userBestScore_travel_and_tourism;
    public int userBestScore_culture;
    public int userBestScore_sport;
    public int userBestScore_health;
    public int userBestScore_science_and_technology;
    public int userBestScore_world_of_adventure;
    public int userBestScore_state_and_society;


    public User(String userName, String userProfile, int allTopics, int eighthGrade, int human, int home, int education,
                int job, int privateLife, int nutrition, int shoppingAndServices,
                int travelAndTourism, int culture, int sport, int health,
                int scienceAndTechnology, int worldOfAdventure, int stateAndSociety) {
        this.userName = userName;
        this.userProfile = userProfile;
        this.userBestScore_all_topics = allTopics;
        this.userBestScore_eighth_grade = eighthGrade;
        this.userBestScore_human = human;
        this.userBestScore_home = home;
        this.userBestScore_education = education;
        this.userBestScore_job = job;
        this.userBestScore_private_life = privateLife;
        this.userBestScore_nutrition = nutrition;
        this.userBestScore_shopping_and_services = shoppingAndServices;
        this.userBestScore_travel_and_tourism = travelAndTourism;
        this.userBestScore_culture = culture;
        this.userBestScore_sport = sport;
        this.userBestScore_health = health;
        this.userBestScore_science_and_technology = scienceAndTechnology;
        this.userBestScore_world_of_adventure = worldOfAdventure;
        this.userBestScore_state_and_society = stateAndSociety;
    }
}
