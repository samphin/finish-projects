package com.ryit.messageserver.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : 刘修
 * @Date : 2019/10/13 8:44
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WinXinEntity {

    private String access_token;
    private String ticket ;
    private String noncestr;
    private String timestamp;
    private String str;
    private String signature;

}
