package com.voice.applicetion.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15/015.
 */

public class Image {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * thumbURL : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1281833774,3797524577&fm=27&gp=0.jpg
         */

        private String thumbURL;

        public String getThumbURL() {
            return thumbURL;
        }

        public void setThumbURL(String thumbURL) {
            this.thumbURL = thumbURL;
        }
    }
}
