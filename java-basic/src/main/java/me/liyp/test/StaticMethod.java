package me.liyp.test;

public class StaticMethod {


    public static class A {
        public static int i;
        public static void foo() {
            System.out.println("This is A.foo()");
        }
    }

    public static class B extends A {
        public static void foo() {
            System.out.println("This is B.foo()");
        }
    }

    public static class C extends A {
        public static int i;
//        public void foo() {
//
//        }
    }

    public static void main(String[] args) {
        A.foo();
        B.foo();
        new A().foo();
        ((A)new B()).foo();
    }

}
