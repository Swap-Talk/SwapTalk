import java.util.ArrayList;
import java.util.List;

public class Friend {
    private String profileImagePath;
    private String name;

    // Constructor
    public Friend(String profileImagePath, String name) {
        this.profileImagePath = profileImagePath;
        this.name = name;
    }

    // Getters
    public String getProfileImagePath() {
        return profileImagePath;
    }

    public String getName() {
        return name;
    }

    // Static method to create dummy friends list
    public static List<Friend> createDummyFriendsList() {
        List<Friend> friends = new ArrayList<>();
        String basePath = "/Users/jinwoo/Desktop/"; // Change to the path where the images are stored

        // Assuming you have 10 placeholder images named profile1.jpg, profile2.jpg, ..., profile10.jpg
        for (int i = 1; i <= 10; i++) {
            friends.add(new Friend(basePath + "profile" + i + ".jpg", "Friend " + i));
        }
        
        return friends;
    }
}
