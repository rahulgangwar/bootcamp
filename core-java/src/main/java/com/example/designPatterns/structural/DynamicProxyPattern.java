package com.example.designPatterns.structural;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DynamicProxyPattern {

  public static void main(String[] args) {
    Map<String, String> proxyInstance =
        (Map<String, String>)
            Proxy.newProxyInstance(
                DynamicProxyPattern.class.getClassLoader(),
                new Class[] {Map.class},
                new DynamicInvocationHandler<Map>(new HashMap<String, String>()));
    proxyInstance.put("rahul", "rahul");
    System.out.println(proxyInstance.get("rahul"));
  }

  public static class DynamicInvocationHandler<T> implements InvocationHandler {

    private final Map<String, Method> methods = new HashMap<>();
    T target;

    DynamicInvocationHandler(T target) {
      this.target = target;

      for (Method method : target.getClass().getMethods()) {
        methods.put(method.getName(), method);
      }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      System.out.println("Invoking method : " + method.getName());
      Method methodToInvoke = methods.get(method.getName());
      return methodToInvoke.invoke(args);
    }
  }
}
