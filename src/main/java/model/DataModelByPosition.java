package model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvNumber;
import lombok.Data;

import java.util.Date;

@Data
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

}
