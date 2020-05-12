package club.banyuan.mall.mgt.service.impl;

import org.junit.Test;

public class TestEasy {
    @Test
    public void test(){
        String a="abcdefgasdsadcd";
        String b="b";
        String[] split = a.split (b);
        System.out.println (split.length);
    }
}
