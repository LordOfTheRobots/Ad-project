package com.ad.utils;

import com.ad.entity.Video;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class VideoUrn extends ConcurrentLinkedQueue<Video> {}