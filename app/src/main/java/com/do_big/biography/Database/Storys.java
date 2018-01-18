package com.do_big.biography.Database;

/**
 * Created by dell on 06-10-2017.
 */

public class Storys {
    String story;
    int _id;


    String storyTitle;
    public Storys(){}
    public Storys(String title, String s) {
        this.storyTitle = title;
        this.story= s;

    }
    public Storys(int id,String title, String s) {
        this._id=id;
        this.storyTitle = title;
        this.story= s;

    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getStory() {
        return story;
    }

    public String setStory(String story) {
        this.story = story;
        return story;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public String setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
        return storyTitle;
    }
}
