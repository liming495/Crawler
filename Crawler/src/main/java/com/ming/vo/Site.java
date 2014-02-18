package com.ming.vo;

/**
 * 没时间写说明拉,帮忙写下 ^-^.
 * User: Ming Li
 * Time: 14-2-13 下午2:40
 */
public class Site {
    private int sign;
    private boolean flag;
    private String requestUrl;

    public Site() {
    }

    public Site(int sign, boolean flag, String requestUrl) {
        this.sign = sign;
        this.flag = flag;
        this.requestUrl = requestUrl;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return "[sign " + sign + "][flag " + flag + "][requestUrl " +requestUrl + "]";
    }

}
