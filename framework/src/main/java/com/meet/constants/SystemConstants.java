package com.meet.constants;

/**常量定义
 * @author lenovo
 */
public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;
    /**
     * 分类状态正常0
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";
    /**
     * 分类状态禁用1
     */
    public static final String CATEGORY_STATUS_DISABLED = "1";
    /**
     * 友链审核通过
     */
    public static final String LINK_STATUS_NORMAL="0";
    /**
     * 友链审核不通过
     */
    public static final String LINK_STATUS_DISABLED="1";
    /**
     * 未审核
     */
    public static final String LINK_STATUS_DEFAULT="2";
    /**
     * 存入到redis中的用户登录前缀
     */

    public static final String LOGIN_USER_PREFIX ="blogLogin" ;

    public static final int  COMMENT_ROOT_ID=-1;
    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";
    /**
     * 权限的状态正常
     */
    public static final String ROLE_STATUS_NORMAL="0";
    /**
     * 权限状态异常
     */
    public static final String ROLE_STATUS_DISABLED="1";
    /**
     * 菜单正常
     */
    public static final String MENU_STATUS_NORMAL ="0" ;
    /**
     * 菜单异常
     */
    public static final String MENU_STATUS_DISABLED ="1";
    /**
     * 分页相关
     */
    public static final long ROLE_PAGE_SIZE = 10;
    public static final long ROLE_PAGE_CURRENT = 1;
    public static final String DEFAULT_PASSWORD ="1234" ;
}