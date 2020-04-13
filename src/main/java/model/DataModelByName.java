package model;

import com.opencsv.bean.*;
import lombok.Data;

import java.util.Date;

@Data
public class DataModelByName {

    @CsvBindByName(required = true)
    private Integer id;

    @CsvBindByName
    private String name;

    @CsvNumber(value = "####.####", writeFormatEqualsReadFormat = false, writeFormat = "###.##")
    @CsvBindByName
    private Double salary;

    @CsvDate(value = "dd-MM-yyyy", writeFormatEqualsReadFormat = false, writeFormat = "yyyy-MM-dd")
    @CsvBindByName(column = "hiring_date")
    private Date hiringDate;

    @CsvIgnore
    private String ignored;

}
