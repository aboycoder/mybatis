package org.apache.ibatis.submitted.ognl_enum;

import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.submitted.ognl_enum.Person.Type;
import org.apache.ibatis.submitted.ognl_enum.PersonMapper.PersonType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EnumWithOgnlTest {
    
    private static SqlSessionFactory sqlSessionFactory;
    
    @BeforeClass
    public static void initDatabase() throws Exception {
        Connection conn = null;

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:mem:ognl_enum", "sa",
                    "");

            Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/ognl_enum/CreateDB.sql");

            ScriptRunner runner = new ScriptRunner(conn);
            runner.setLogWriter(null);
            runner.setErrorLogWriter(new PrintWriter(System.err));
            runner.runScript(reader);
            conn.commit();
            reader.close();

            reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/ognl_enum/ibatisConfig.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    @Test
    public void testEnumWithOgnl() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> persons = personMapper.selectAllByType(null);
        Assert.assertEquals("Persons must contain 3 persons", 3, persons.size());
      sqlSession.close();
    }

  @Test
    public void testEnumWithOgnlDirector() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> persons = personMapper.selectAllByType(Person.Type.DIRECTOR);
        Assert.assertEquals("Persons must contain 1 persons", 1, persons.size());
    sqlSession.close();
    }

    @Test
    public void testEnumWithOgnlDirectorNameAttribute() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> persons = personMapper.selectAllByTypeNameAttribute(Person.Type.DIRECTOR);
        Assert.assertEquals("Persons must contain 1 persons", 1, persons.size());
      sqlSession.close();
    }

  @Test
    public void testEnumWithOgnlDirectorWithInterface() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> persons = personMapper.selectAllByTypeWithInterface(new PersonType() {
            public Type getType() {
                return Person.Type.DIRECTOR;
            }
        });
        Assert.assertEquals("Persons must contain 1 persons", 1, persons.size());
    sqlSession.close();
    }
    @Test
    public void testEnumWithOgnlDirectorNameAttributeWithInterface() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> persons = personMapper.selectAllByTypeNameAttributeWithInterface(new PersonType() {
            public Type getType() {
                return Person.Type.DIRECTOR;
            }
        });
        Assert.assertEquals("Persons must contain 1 persons", 1, persons.size());
      sqlSession.close();
    }
}
