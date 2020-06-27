package tests.data;

import model.DataModelByName;
import model.DataModelByPosition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

final public class DataUtils {

    private DataUtils() {
    }

    static public List<DataModelByPosition> buildDataModelByPosition() {
        List<DataModelByPosition> data = new ArrayList<>();

        Instant instant;
        try {
            instant = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-11").toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        {
            DataModelByPosition model = new DataModelByPosition();
            model.setId(1);
            model.setName("john");
            model.setSalary(12.34);
            model.setHiringDate(Date.from(instant));
            data.add(model);
        }
        {
            DataModelByPosition model = new DataModelByPosition();
            model.setId(2);
            model.setName("mary");
            model.setSalary(123.456);
            model.setHiringDate(Date.from(instant.plus(1, ChronoUnit.DAYS)));
            data.add(model);
        }
        {
            DataModelByPosition model = new DataModelByPosition();
            model.setId(3);
            model.setName("paul");
            model.setSalary(1234.5678);
            model.setHiringDate(Date.from(instant.plus(2, ChronoUnit.DAYS)));
            data.add(model);
        }
        return data;
    }

    static public List<DataModelByName> buildDataModelByName() {
        List<DataModelByName> data = new ArrayList<>();

        Instant instant;
        try {
            instant = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-11").toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        {
            DataModelByName model = new DataModelByName();
            model.setId(1);
            model.setName("john");
            model.setSalary(12.34);
            model.setHiringDate(Date.from(instant));
            data.add(model);
        }
        {
            DataModelByName model = new DataModelByName();
            model.setId(2);
            model.setName("mary");
            model.setSalary(123.456);
            model.setHiringDate(Date.from(instant.plus(1, ChronoUnit.DAYS)));
            data.add(model);
        }
        {
            DataModelByName name = new DataModelByName();
            name.setId(3);
            name.setName("paul");
            name.setSalary(1234.5678);
            name.setHiringDate(Date.from(instant.plus(2, ChronoUnit.DAYS)));
            data.add(name);
        }
        return data;
    }

}
