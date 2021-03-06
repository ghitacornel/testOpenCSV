package model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvNumber;

import java.util.Date;
import java.util.Objects;

public class DataModelByPosition {

    @CsvBindByPosition(position = 0, required = true)
    private Integer id;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvNumber(value = "####.####", writeFormatEqualsReadFormat = false, writeFormat = "###.##")
    @CsvBindByPosition(position = 2)
    private Double salary;

    @CsvDate(value = "dd-MM-yyyy", writeFormatEqualsReadFormat = false, writeFormat = "yyyy-MM-dd")
    @CsvBindByPosition(position = 3)
    private Date hiringDate;

    @CsvIgnore
    private String ignored;

    // and garbage

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModelByPosition that = (DataModelByPosition) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(salary, that.salary) &&
                Objects.equals(hiringDate, that.hiringDate) &&
                Objects.equals(ignored, that.ignored);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, hiringDate, ignored);
    }

    @Override
    public String toString() {
        return "DataModelByPosition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", hiringDate=" + hiringDate +
                ", ignored='" + ignored + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    public String getIgnored() {
        return ignored;
    }

    public void setIgnored(String ignored) {
        this.ignored = ignored;
    }
}
