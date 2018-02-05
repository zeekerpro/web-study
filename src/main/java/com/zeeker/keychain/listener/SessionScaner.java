/**
 * @fileName :     SessionScaner
 * @author :       zeeker
 * @date :         05/02/2018 12:43 AM
 * @description :  自定义session扫描器
 *
 * 扫描session，销毁session
 */

package com.zeeker.keychain.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SessionScaner implements HttpSessionListener, ServletContextListener {

    Lock lock = new ReentrantLock();

    // 存放 session 的容器, 保证线程安全
    private List<HttpSession> sessions = Collections.synchronizedList(new LinkedList<>());

    // 5分钟内没有使用 session 则销毁
    private static  final long EXPIRE_TIME = 5 * 60 * 1000;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        // 线程安全：1. 多个的用户同时访问 2. 执行定时器扫描任务时会遍历sessions，此时用户访问会有线程安全问题
        lock.lock();
        sessions.add(session);
        lock.unlock();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Timer timer = new Timer();
        // 设置定时器每五分钟进行一次session扫描
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lock.lock();
                ListIterator<HttpSession> sessionListIterator = sessions.listIterator();
                while (sessionListIterator.hasNext()){
                    HttpSession session = sessionListIterator.next();
                    if (System.currentTimeMillis() - session.getLastAccessedTime() > EXPIRE_TIME){
                        session.invalidate();
                        sessionListIterator.remove();
                    }
                }
                lock.unlock();
            }
        }, 0, EXPIRE_TIME);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
