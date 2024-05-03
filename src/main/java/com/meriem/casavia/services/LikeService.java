package com.meriem.casavia.services;

import com.meriem.casavia.entities.Like;

public interface LikeService {
    Like addLike(long user,long hebergement);
    void removeLike(long id);
}
