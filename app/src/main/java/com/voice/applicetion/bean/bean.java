package com.voice.applicetion.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/12/012.
 */

public class bean {

    /**
     * results_recognition : ["请说出你想知道的词语"]
     * origin_result : {"corpus_no":6555671424798466528,"err_no":0,"result":{"word":["请说出你想知道的词语","请说出你想知道词语","请说祝你想知道的词语","请说出你想知道自己","请说出你想知道的词与","请说出你想知道得词语","请说出你想知道自已","请说祝你想知道词语","请说出你想知道词与","请说出你想知到词语"]},"sn":"51a4855a-159a-40d8-9367-ed8e301b5f22","voice_energy":16717.9140625}
     * error : 0
     * best_result : 请说出你想知道的词语
     * result_type : final_result
     */

    private OriginResultBean origin_result;
    private int error;
    private String best_result;
    private String result_type;
    private List<String> results_recognition;

    public OriginResultBean getOrigin_result() {
        return origin_result;
    }

    public void setOrigin_result(OriginResultBean origin_result) {
        this.origin_result = origin_result;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getBest_result() {
        return best_result;
    }

    public void setBest_result(String best_result) {
        this.best_result = best_result;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    public List<String> getResults_recognition() {
        return results_recognition;
    }

    public void setResults_recognition(List<String> results_recognition) {
        this.results_recognition = results_recognition;
    }

    public static class OriginResultBean {
        /**
         * corpus_no : 6555671424798466528
         * err_no : 0
         * result : {"word":["请说出你想知道的词语","请说出你想知道词语","请说祝你想知道的词语","请说出你想知道自己","请说出你想知道的词与","请说出你想知道得词语","请说出你想知道自已","请说祝你想知道词语","请说出你想知道词与","请说出你想知到词语"]}
         * sn : 51a4855a-159a-40d8-9367-ed8e301b5f22
         * voice_energy : 16717.9140625
         */

        private long corpus_no;
        private int err_no;
        private ResultBean result;
        private String sn;
        private double voice_energy;

        public long getCorpus_no() {
            return corpus_no;
        }

        public void setCorpus_no(long corpus_no) {
            this.corpus_no = corpus_no;
        }

        public int getErr_no() {
            return err_no;
        }

        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public double getVoice_energy() {
            return voice_energy;
        }

        public void setVoice_energy(double voice_energy) {
            this.voice_energy = voice_energy;
        }

        public static class ResultBean {
            private List<String> word;

            public List<String> getWord() {
                return word;
            }

            public void setWord(List<String> word) {
                this.word = word;
            }
        }
    }
}
