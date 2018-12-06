package cos.mos.library.constant;

import cos.mos.library.utils.UText;

/**
 * @Description: <p>
 * @Author: Kosmos
 * @Date: 2018.11.13 12:12
 * @Email: KosmoSakura@gmail.com
 */
public class KConfig {
    public static String Describe;//接口描述
    private static String BASE_URL;//base url


    public static String getBaseUrl() {
        if (UText.isEmpty(BASE_URL)) {
            BASE_URL = "";
        }
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }
}
