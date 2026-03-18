package com.ad.utils;

import com.ad.entity.Channel;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ChannelUrn extends ConcurrentLinkedQueue<Channel> {}