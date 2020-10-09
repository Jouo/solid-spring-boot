package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import com.web.services.validator.intercaces.EpochAfterCurrentDate;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EpochBanDateAPI {

    @NotNull
    @EpochAfterCurrentDate
    @ApiModelProperty(position = 1, example = "1603411200")
    private Long date = null;

    public EpochBanDateAPI() {}

    public EpochBanDateAPI(Long date) {
        this.date = date;
    }

    public static EpochBanDateAPI fromHours(int hours) {
        Instant time = Instant.now().plus(hours, ChronoUnit.HOURS);
        Long date = time.getEpochSecond();
        return new EpochBanDateAPI(date);
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter().add("date", date);
        return formatter.format();
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
