package pl.coderslab.myFinance.model;

import java.io.Serializable;
import java.util.Objects;

public class UserReportId implements Serializable {

    private Integer user;
    private Integer report;

    public UserReportId() {
    }

    public UserReportId(Integer user, Integer report) {
        this.user = user;
        this.report = report;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getReport() {
        return report;
    }

    public void setReport(Integer report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReportId that = (UserReportId) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(report, that.report);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, report);
    }
}
