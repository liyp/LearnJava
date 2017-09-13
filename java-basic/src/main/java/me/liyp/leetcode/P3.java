/*
 * Copyright © 2017 liyp (liyp.yunpeng@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.liyp.leetcode;

public class P3 {
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int window = 1, left = 0, right = 1;
        while(right < chars.length) {
            char ch = chars[right];
            int index = find(chars, left, right, ch);
            if (index != -1) {
                left = index + 1;
            }
            right++;
            window = Math.max(window, right - left);
        }
        return window;
    }

    private int find(char[] cs, int l, int r, char ch) {
       for (int i=l; i<r; i++) {
           if (cs[i] == ch) {
               return i;
           }
       }
       return -1;
    }

    public static void main(String[] args) {
        String s = "dvdf";
        System.out.println(new P3().lengthOfLongestSubstring(s));
    }
}
