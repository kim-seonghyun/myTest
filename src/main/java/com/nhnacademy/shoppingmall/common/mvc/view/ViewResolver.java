package com.nhnacademy.shoppingmall.common.mvc.view;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ViewResolver {

    public static final String DEFAULT_PREFIX="/WEB-INF/views/";
    public static final String DEFAULT_POSTFIX=".jsp";
    public static final String REDIRECT_PREFIX="redirect:";
    public static final String DEFAULT_SHOP_LAYOUT="/WEB-INF/views/layout/shop.jsp";
    public static final String DEFAULT_ADMIN_LAYOUT="/WEB-INF/views/layout/admin.jsp";
    public static final String LAYOUT_CONTENT_HOLDER = "layout_content_holder";

    public static final String IMAGE_DIR = "resources/";

    public static final String ADMIN_URL = "/admin/";
    public static final String ADMIN_URL2 = "/for_admin/";
    private final String prefix;
    private final String postfix;

    /**
     * Controller가 반환하는 view Name을 WEB-INF/views 하위에 있는 JSP 파일로 대응하는 역할
     */
    public ViewResolver(){
        this(DEFAULT_PREFIX,DEFAULT_POSTFIX);
    }
    public ViewResolver(String prefix, String postfix) {
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public static String getImageDir(String fileName) {
        return IMAGE_DIR + fileName;
    }

    public  String getPath(String viewName){
        //todo#6-1  postfix+viewNAme+postfix 반환 합니다.
        if(viewName.startsWith("/")){
            return prefix + viewName.substring(1) + postfix;
        }else{
            return prefix + viewName + postfix;

        }
    }

    public boolean isRedirect(String viewName){
        //todo#6-2 REDIRECT_PREFIX가 포함되어 있는지 체크 합니다.
        return viewName.toLowerCase().contains(REDIRECT_PREFIX);
    }

    public String getRedirectUrl(String viewName){
        //todo#6-3 REDIRECT_PREFIX를 제외한 url을 반환 합니다.
        if (isRedirect(viewName.toLowerCase())) {
            return viewName.substring(REDIRECT_PREFIX.length());
        }
        return viewName;
    }

    public String getLayOut(String viewName){

        /*todo#6-4 viewName에
           /admin/경로가 포함되었다면 DEFAULT_ADMIN_LAYOUT 반환 합니다.
           /admin/경로가 포함되어 있지않다면 DEFAULT_SHOP_LAYOUT 반환 합니다.
        */
        if (viewName.contains(ADMIN_URL2) || viewName.contains(ADMIN_URL)) {
            return DEFAULT_ADMIN_LAYOUT;
        }else{
            return DEFAULT_SHOP_LAYOUT;
        }
    }
}
