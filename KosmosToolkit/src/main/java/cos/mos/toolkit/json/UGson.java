package cos.mos.toolkit.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 可用于简单的json解析
 * @Author Kosmos
 * @Date 2018年10月08日 16:30
 * @Email KosmoSakura@gmail.com
 * @Tip 2019.11.7-添加gson解析容错格式
 * @Tip 2020.7.28 优化gson列表解析
 */
public class UGson {
    private static Gson gson;

    static {
        gson = new GsonBuilder()
            .setLenient()//宽容的解析，默认情况下，Gson是严格的，只接受指定的JSON
            .enableComplexMapKeySerialization()//启用复杂映射键序列化
            .serializeSpecialFloatingPointValues()//序列化特殊浮点值
            .serializeNulls()//序列化null
            .setPrettyPrinting()//将Gson配置为输出Json，以便在页面中进行漂亮的打印。这个选项有影响Json序列化。
            .disableHtmlEscaping() //禁止转义html标签:默认情况下，Gson会转义HTML字符，如&lt等。使用此选项进行配置按原样传递HTML字符
            .setDateFormat("yyyy-MM-dd HH:mm:ss")// 设置日期时间格式，另有2个重载方法 ,在序列化和反序化时均生效
            .disableInnerClassSerialization()// 禁此序列化内部类
            .registerTypeAdapter(Double.class, new DoubleConverter())
            .registerTypeAdapter(Float.class, new FloatConverter())
            .registerTypeAdapter(Long.class, new LongConverter())
            .registerTypeAdapter(Integer.class, new IntConverter())
            .registerTypeAdapter(Boolean.class, new BoolConverter())
            .registerTypeAdapter(String.class, new StringConverter())
//            .excludeFieldsWithoutExposeAnnotation()// 不转换没有@Expose注释的字段
//            .generateNonExecutableJson() //生成不可执行的Json（多了 )]}' 这4个字符）：通过在生成的JSON前面加上一些前缀，使输出JSON在Javascript中不可执行
            .create();
    }

    public static Gson getGson() {
        return gson;
    }

    /**
     * @return 实体类转换的字符串
     */
    public static <T> String toJson(T bean) {
        return gson.toJson(bean);
    }

    public static <T> String toJson(T bean, Type type) {
        return gson.toJson(bean, type);
    }

    /**
     * @return 返回一个实体类对象 JsonSyntaxException
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }

    public static <T> List<T> fromJsonList(String json) {
        return gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
    }

    public static <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        ArrayList<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * @param list<A> 待转泛型列表
     * @param cls     目标类型Class
     * @param <T>     目标泛型
     * @return 返回一个转换类型的列表
     */
    public static <T> ArrayList<T> fromJsonList(ArrayList list, Class<T> cls) {
        ArrayList<T> dtoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i);
            String json = gson.toJson(object);
            T dto = gson.fromJson(json, cls);
            dtoList.add(dto);
        }
        return dtoList;
    }

//---------------------------------------------------------------------------------------------
//下面的代码仅测试用
//---------------------------------------------------------------------------------------------

    /**
     * @param obj 被解析的实例化对象如 Bean() ArrayList<Bean>()
     * @param <T> 解析任何类型的数据
     * @Tip 栗子
     * Integer xx = jsonToAny("json", 1);
     * ArrayList<Integer> ss = jsonToAny("json", new ArrayList<Integer>());
     */
    public static <T> T toAny(String json, T obj) {
        try {
            return gson.fromJson(json, getRealType(obj));
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    private static Type getRealType(Object obj) {
        Type[] ts = obj.getClass().getGenericInterfaces();
        for (Type type : ts) {
            if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                return ((ParameterizedType) type).getActualTypeArguments()[0];
            }
        }
        return null;
    }
}
