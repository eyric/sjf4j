import com.levin.sjf4j.core.JSON;
import com.levin.sjf4j.core.JSONBuilderProvider;
import org.junit.Test;

public class JSONTest {
    @Test
    public void test() {
        JSON json = JSONBuilderProvider.create().build();
        Person person = new Person();
        person.setAge(1);
        person.setName("xxx");
        System.out.println(json.toJson(person));
    }

    @Test
    public void testGson(){
        JSON json = JSONBuilderProvider.create("gson").build();
        Person person = new Person();
        person.setAge(1);
        person.setName("xxx");
        System.out.println(json.toJson(person));
    }

    @Test
    public void testFastJson(){
        JSON json = JSONBuilderProvider.create("fastjson").build();
        Person person = new Person();
        person.setAge(1);
        person.setName("xxx");
        System.out.println(json.toJson(person));
    }

    @Test
    public void testJackson(){
        JSON json = JSONBuilderProvider.create("jackson").build();
        Person person = new Person();
        person.setAge(1);
        person.setName("xxx");
        System.out.println(json.toJson(person));
    }
}
