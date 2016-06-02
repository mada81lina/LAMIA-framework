package framework.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class User implements Cloneable {

    private static final List<User> USERS = fromFile();
    private String id;
    private String suffix;

    private Boolean randomize = true;

    private String login;
    private String email;

    private String lastName;
    private String firstName;


    private User() {
    }

    public static List<User> fromFile() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            ClassLoader classLoader = Thread.currentThread()
                    .getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("TestData/BM/users.json");
            return mapper.readValue(input,
                    new TypeReference<List<User>>() {
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param id     - user value
     * @param suffix -  suffix to be used
     * @return the user if it exists, null if not.
     */
    public static User getData(String id, String suffix) {
        for (User user : USERS) {
            if (user.getId().equals(id)) {
                try {
                    User userToReturn = (User) user.clone();
                    userToReturn.suffix = suffix;
                    return userToReturn;
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getEmail() {
        String randomEmail;
        randomEmail = email.split("@")[0] + suffix + "@" + email.split("@")[1];
        return randomize ? randomEmail : email;
    }

    public String getFirstName() {
        return TestData.getRandomIfNeeded(firstName, randomize, suffix);
    }

    public String getId() {
        return id;
    }

}
