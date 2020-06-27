package tests.writers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import model.DataModelByName;
import org.junit.Assert;
import org.junit.Test;
import tests.data.DataUtils;
import tests.data.FileUtils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class TestByName {

    @Test
    public void testRead() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByName.csv"));

        List<DataModelByName> expected = DataUtils.buildDataModelByName();
        List<DataModelByName> actual = new CsvToBeanBuilder<DataModelByName>(reader).
                withType(DataModelByName.class).
                build().parse();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testReadWithHeader() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByNameWithHeader.csv"));

        List<DataModelByName> expected = DataUtils.buildDataModelByName();
        List<DataModelByName> actual = new CsvToBeanBuilder<DataModelByName>(reader).
                withType(DataModelByName.class).
                build().parse();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testReadWithEmptyLines() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByNameWithEmptyLines.csv"));

        List<DataModelByName> expected = DataUtils.buildDataModelByName();
        List<DataModelByName> actual = new CsvToBeanBuilder<DataModelByName>(reader).
                withType(DataModelByName.class).
                withIgnoreEmptyLine(true).
                build().parse();

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testReadWithErrors() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByNameWithErrors.csv"));

        try {
            new CsvToBeanBuilder<DataModelByName>(reader).
                    withType(DataModelByName.class).
                    build().parse();
        } catch (Exception e) {
            Assert.assertEquals("Error parsing CSV line: 3. [,mary,123.456,12-11-2020]", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testReadWithErrorsAndData() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByNameWithErrorsAndData.csv"));

        CsvToBean<DataModelByName> csvToBean = new CsvToBeanBuilder<DataModelByName>(reader).
                withType(DataModelByName.class).
                withThrowExceptions(false).
                build();

        List<DataModelByName> actual = csvToBean.parse();
        List<DataModelByName> expected = DataUtils.buildDataModelByName();
        Assert.assertEquals(expected, actual);

        List<CsvException> exceptions = csvToBean.getCapturedExceptions();
        Assert.assertEquals(2, exceptions.size());
        CsvException exception1 = exceptions.get(0);
        Assert.assertEquals("Field 'id' is mandatory but no value was provided.", exception1.getMessage());
        Assert.assertEquals(5, exception1.getLineNumber());
        Assert.assertEquals(" errorNoRequiredId 123.456 12-11-2020", String.join(" ", Arrays.asList(exception1.getLine())));
        CsvException exception2 = exceptions.get(1);
        Assert.assertNull(exception2.getMessage());
        Assert.assertEquals(6, exception2.getLineNumber());
        Assert.assertEquals("5 dateError 1234.6789 2020/11/11", String.join(" ", Arrays.asList(exception2.getLine())));

    }

    @Test
    public void testWrite() {

        List<DataModelByName> data = DataUtils.buildDataModelByName();

        StringWriter writer = new StringWriter();
        try {
            new StatefulBeanToCsvBuilder<DataModelByName>(writer).
                    withApplyQuotesToAll(false).
                    withLineEnd(System.lineSeparator()).
                    build().write(data);
        } catch (Exception e) {
            throw new RuntimeException("cannot write csv content", e);
        }

        String actual = writer.toString();
        String expected = FileUtils.readFile("outputWriteByName.csv");
        Assert.assertEquals(expected, actual);
    }

}
