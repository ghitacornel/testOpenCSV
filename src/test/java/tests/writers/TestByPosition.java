package tests.writers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import model.DataModelByPosition;
import org.junit.Assert;
import org.junit.Test;
import tests.data.DataUtils;
import tests.data.FileUtils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class TestByPosition {

    @Test
    public void testRead() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByPosition.csv"));

        List<DataModelByPosition> expected = DataUtils.buildDataModelByPosition();
        List<DataModelByPosition> actual = new CsvToBeanBuilder<DataModelByPosition>(reader).
                withType(DataModelByPosition.class).
                build().parse();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testReadWithHeader() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByPositionWithHeader.csv"));

        List<DataModelByPosition> expected = DataUtils.buildDataModelByPosition();
        List<DataModelByPosition> actual = new CsvToBeanBuilder<DataModelByPosition>(reader).
                withType(DataModelByPosition.class).
                withSkipLines(1).
                build().parse();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testReadWithEmptyLines() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByPositionWithEmptyLines.csv"));

        List<DataModelByPosition> expected = DataUtils.buildDataModelByPosition();
        List<DataModelByPosition> actual = new CsvToBeanBuilder<DataModelByPosition>(reader).
                withType(DataModelByPosition.class).
                withIgnoreEmptyLine(true).
                build().parse();

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testReadWithErrors() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByPositionWithErrors.csv"));

        try {
            new CsvToBeanBuilder<DataModelByPosition>(reader).
                    withType(DataModelByPosition.class).
                    build().parse();
        } catch (Exception e) {
            Assert.assertEquals("Error parsing CSV line: 2. [,mary,123.456,12-11-2020]", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testReadWithErrorsAndData() {

        StringReader reader = new StringReader(FileUtils.readFile("inputReadByPositionWithErrorsAndData.csv"));

        CsvToBean<DataModelByPosition> csvToBean = new CsvToBeanBuilder<DataModelByPosition>(reader).
                withType(DataModelByPosition.class).
                withThrowExceptions(false).
                build();

        List<DataModelByPosition> actual = csvToBean.parse();
        List<DataModelByPosition> expected = DataUtils.buildDataModelByPosition();
        Assert.assertEquals(expected, actual);

        List<CsvException> exceptions = csvToBean.getCapturedExceptions();
        Assert.assertEquals(2, exceptions.size());
        CsvException exception1 = exceptions.get(0);
        Assert.assertEquals("Field 'id' is mandatory but no value was provided.", exception1.getMessage());
        Assert.assertEquals(4, exception1.getLineNumber());
        Assert.assertEquals(" errorNoRequiredId 123.456 12-11-2020", String.join(" ", Arrays.asList(exception1.getLine())));
        CsvException exception2 = exceptions.get(1);
        Assert.assertNull(exception2.getMessage());
        Assert.assertEquals(5, exception2.getLineNumber());
        Assert.assertEquals("5 dateError 1234.6789 2020/11/11", String.join(" ", Arrays.asList(exception2.getLine())));

    }

    @Test
    public void testWrite() {

        List<DataModelByPosition> data = DataUtils.buildDataModelByPosition();

        StringWriter writer = new StringWriter();
        try {
            new StatefulBeanToCsvBuilder<DataModelByPosition>(writer).
                    withApplyQuotesToAll(false).
                    withLineEnd(System.lineSeparator()).
                    build().write(data);
        } catch (Exception e) {
            throw new RuntimeException("cannot write csv content", e);
        }

        String actual = writer.toString();
        String expected = FileUtils.readFile("outputWriteByPosition.csv");
        Assert.assertEquals(expected, actual);
    }

}
