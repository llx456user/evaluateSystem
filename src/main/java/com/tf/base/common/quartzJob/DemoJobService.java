package com.tf.base.common.quartzJob;

import org.springframework.stereotype.Service;

@Service
public class DemoJobService {

	public void excute() {
		
		System.out.println("this is a demo quartz job execute.");
	}
}
