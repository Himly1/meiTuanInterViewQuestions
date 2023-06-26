package org.meituan.dependency_manager;

public interface DependencyManager {
    <T> T getDependencyByType(Class<T> clz);
}
