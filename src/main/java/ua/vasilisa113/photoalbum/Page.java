package ua.vasilisa113.photoalbum;

public interface Page {
    String getName();
    void setName(String name);
    byte[] getLogo();
    void setLogo(byte[] logo);
    String[] getMenu();
    void setMenu(String[] menu);
    byte[] getPhotoBackground();
    void setPhotoBackground(byte[] photoBackground);
    String getDescription();
    void setDescription(String description);
    String[] getTags();
    void setTags(String[] tags);
    String getBack();
    void setBack(String back);
    String[] getContact();
    void setContact(String[] contact);
}
