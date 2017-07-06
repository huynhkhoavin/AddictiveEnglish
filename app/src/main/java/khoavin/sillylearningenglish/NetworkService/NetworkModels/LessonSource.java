package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KhoaVin on 05/07/2017.
 */

public class LessonSource {

    @SerializedName("source_id")
    @Expose
    private String sourceId;
    @SerializedName("source_name")
    @Expose
    private String sourceName;
    @SerializedName("source_image_url")
    @Expose
    private String sourceImageUrl;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceImageUrl() {
        return sourceImageUrl;
    }

    public void setSourceImageUrl(String sourceImageUrl) {
        this.sourceImageUrl = sourceImageUrl;
    }

}