import 'dart:async';
import 'dart:collection';

class Cache<T> {
  final map = HashMap<String, T>();

  bool contains(String key) {
    return map.containsKey(key);
  }

  Future<T> get(String key) {
    return Future.value(map[key]);
  }

  put(String key, object) {
    map[key] = object;
  }
}
