/**
 * Created on 2016/3/4
 */
package org.sprout.fetch.spec;

/**
 * 网络服务异常信息
 * <p/>
 *
 * @author Wythe
 */
public enum FetchError {

    UNKNOWN_ERR(-1, "FetchService occurred unknown error."),
    NETWORK_ERR(0, "FetchService unable to access the network."),
    REQUEST_ERR(1, "FetchService unable to request the remote data."),
    WRITE_ERR(2, "FetchService write data fault."),
    RETRY_ERR(3, "FetchService retry serv fault."),
    SPACE_ERR(4, "FetchService not have enough storage space."),
    PARAM_ERR(5, "FetchService exists invalid parameter."),
    QUEUE_ERR(6, "FetchService unable to join the queue."),
    RECORD_ERR(7, "FetchService recorder handle error."),
    FOLDER_ERR(8, "FetchService create target folder error."),
    RESPFILE_ERR(9, "FetchService the response of the request is empty."),
    SAVESIZE_LESS_ERR(10, "FetchService the saved less than real size error."),
    SAVESIZE_MORE_ERR(11, "FetchService the saved more than real size error."),
    TEMPFILE_CHAN_ERR(12, "FetchService open the temp file io error."),
    SAVEFILE_DATA_ERR(13, "FetchService saved the real file data error."),
    TEMPFILE_DATA_ERR(14, "FetchService saved the temp file data error."),
    TEMPFILE_CONVERT_ERR(15, "FetchService convert the temp file to real file error."),
    FILESIZE_CONFLICT_ERR(16, "FetchService the response data size of the request conflict."),
    SAVETASK_CONFLICT_ERR(17, "FetchService perform the save task conflict.");

    private final int code;

    private final String message;

    FetchError(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
