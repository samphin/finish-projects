package com.ryit.walletnewsserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 敏感词过滤 工具类   -- 【匹配度高，可以使用】
 * 《高效精准》敏感字&词过滤
 *
 * @author hubiao
 * @version 0.1
 * @CreateDate 2015年4月16日 15:28:32
 */
public class SensitiveWordUtil {

    private StringBuilder replaceAll;
    private String encoding = "UTF-8";
    private String replaceStr = "*";
    private int replaceSize = 500;
    private static String FILE_NAME = "CensorWords.txt";
    private List<String> arrayList;
    /**
     * 包含的敏感词列表，过滤掉重复项
     */
    public Set<String> sensitiveWordSet;
    /**
     * 包含的敏感词列表，包括重复项，统计次数
     */
    public List<String> sensitiveWordList;

    /**
     * 文件要求路径在src或resource下，默认文件名为CensorWords.txt
     *
     * @param fileName 词库文件名(含后缀)
     */
    public SensitiveWordUtil(String fileName) {
        FILE_NAME = fileName;
    }

    /**
     * @param replaceStr  敏感词被转换的字符
     * @param replaceSize 初始转义容量
     */
    public SensitiveWordUtil(String replaceStr, int replaceSize) {
        this.replaceStr = FILE_NAME;
        this.replaceSize = replaceSize;
    }

    public SensitiveWordUtil() {
    }

    /**
     * @param str 将要被过滤信息
     * @return 过滤后的信息
     */
    public String filterInfo(String str) {
        sensitiveWordSet = new HashSet<>();
        sensitiveWordList = new ArrayList<>();
        StringBuilder buffer = new StringBuilder(str);
        HashMap<Integer, Integer> hash = new HashMap<>(arrayList.size());
        String temp;
        for (int x = 0; x < arrayList.size(); x++) {
            temp = arrayList.get(x);
            int findIndexSize = 0;
            for (int start = -1; (start = buffer.indexOf(temp, findIndexSize)) > -1; ) {
                //从已找到的后面开始找
                findIndexSize = start + temp.length();
                //起始位置
                Integer mapStart = hash.get(start);
                //满足1个，即可更新map
                if (mapStart == null || (mapStart != null && findIndexSize > mapStart)) {
                    hash.put(start, findIndexSize);
                }
            }
        }
        Collection<Integer> values = hash.keySet();
        for (Integer startIndex : values) {
            Integer endIndex = hash.get(startIndex);
            //获取敏感词，并加入列表，用来统计数量
            String sensitive = buffer.substring(startIndex, endIndex);
            //添加敏感词到集合
            if (!sensitive.contains("*")) {
                sensitiveWordSet.add(sensitive);
                sensitiveWordList.add(sensitive);
            }
            buffer.replace(startIndex, endIndex, replaceAll.substring(0, endIndex - startIndex));
        }
        hash.clear();

        return buffer.toString();
    }

    /**
     * 初始化敏感词库
     */
    public void initializationWork() {
        replaceAll = new StringBuilder(replaceSize);
        for (int x = 0; x < replaceSize; x++) {
            replaceAll.append(replaceStr);
        }
        //加载词库  
        arrayList = new ArrayList<>();
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
            read = new InputStreamReader(SensitiveWordUtil.class.getClassLoader().getResourceAsStream(FILE_NAME), encoding);
            bufferedReader = new BufferedReader(read);
            for (String txt = null; (txt = bufferedReader.readLine()) != null; ) {
                if (!arrayList.contains(txt)) {
                    arrayList.add(txt);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != read) {
                    read.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检验敏感词并替换
     *
     * @param str
     * @return
     */
    public static String startReplaceStr(String str) {
        SensitiveWordUtil sw = new SensitiveWordUtil(FILE_NAME);
        sw.initializationWork();
        str = sw.filterInfo(str);
        return str;
    }

}

