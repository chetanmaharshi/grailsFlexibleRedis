package com.qwerty

import grails.transaction.Transactional

@Transactional
class CustomRedisFlexibleCacheService {

    def redisFlexibleCacheService

    def doCacheList(Class c, String key, String group, Integer ttl, Boolean isRettachToSession , Closure closure) {
        log.debug "doCacheList ${c.getName()}"
        String className =  c.getName()
        if(className && className?.contains(".")){
            className = className.substring(className.lastIndexOf(".")+1,className.length())
        }
        log.debug "After filter className  ${className}"
        if(closure){
            def listIds = redisFlexibleCacheService.doCache(key, group, ttl, isRettachToSession, {
                return closure()
            })
            if(!listIds){
                redisFlexibleCacheService.evictCache( key, {
                    log.debug('evicted :' + key);
                })
                listIds = redisFlexibleCacheService.doCache(key, group, ttl, isRettachToSession, {
                    return closure()
                })
            }
            def returnObjectList = []
            listIds?.each{ id ->
                log.debug "Id to ${id}"
                def eachObject = redisFlexibleCacheService.doCache(className+':'+id, "${className}Group", ttl, isRettachToSession, {
                    return c.get(id)
                })
                if(eachObject){
                    returnObjectList.add(eachObject)
                }
            }
            return returnObjectList
        }else{
            return closure()?:null
        }
    }

    def doCachePut(String key, String group, Integer ttl, Boolean isRettachToSession , Closure closure) {
        if(!closure){
            return closure()?:null
        }
        def updatedObject = closure()

        redisFlexibleCacheService.evictCache( key, {
            log.debug('evicted :' + key);
        })
        updatedObject = redisFlexibleCacheService.doCache(key, group, ttl, isRettachToSession, {
            return updatedObject
        })

        return updatedObject
    }

    def doCachePost(Class c , String key, String group, Integer ttl, Boolean isRettachToSession , Closure closure) {
        log.debug "doCachePost ${c.getName()}"
        String className =  c.getName()
        if(className && className?.contains(".")){
            className = className.substring(className.lastIndexOf(".")+1,className.length())
        }
        log.debug "After filter className  ${className}"
        if(!closure){
            return closure()?:null
        }
        def postObject = closure()
        List<Long> list = redisFlexibleCacheService.doCache(key, group, ttl, isRettachToSession, { return null })// List of Id of object
        if(!list){
            postObject = redisFlexibleCacheService.doCache(className+':'+postObject?.id, "${className}Group", ttl, isRettachToSession, {
                return postObject
            })
            return postObject
        }
        list.add(postObject.id)
        redisFlexibleCacheService.evictCache( key, {
            log.debug('evicted :' + key);
        })
        postObject = redisFlexibleCacheService.doCache(className+':'+postObject?.id, "${className}Group", ttl, isRettachToSession, {
            return postObject
        })
        return postObject
    }

    //delete wala bhi handle
    void doCacheDelete(Class c , String key, String group, Integer ttl, Boolean isRettachToSession , Closure closure){
        log.debug "doCacheDelete ${c.getName()}"
        String className =  c.getName()
        if(className && className?.contains(".")){
            className = className.substring(className.lastIndexOf(".")+1,className.length())
        }
        log.debug "After filter className  ${className}"
        if(!closure){
            return
        }
        def deleteObjId = closure()
        List<Long> list = redisFlexibleCacheService.doCache(key, group, ttl, isRettachToSession, { return null })
        if(!list){
            redisFlexibleCacheService.evictCache( className+':'+deleteObjId, {
                log.debug('evicted :' + key);
            })
        }
        list.remove(deleteObjId)
        redisFlexibleCacheService.evictCache( className+':'+deleteObjId, {
            log.debug('evicted :' + key);
        })
        redisFlexibleCacheService.evictCache( key, {
            log.debug('evicted :' + key);
        })
        redisFlexibleCacheService.doCache(key, group, ttl, isRettachToSession, {
            return list
        })
    }
}
