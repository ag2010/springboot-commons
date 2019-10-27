package com.hr.common.verification;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * 验证码配置
 * @author chenqiuxu
 *
 */

@Configuration 
public class CaptchaConfig {

	@Bean(name="captchaProducer")  
    public DefaultKaptcha getKaptchaBean(){  
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();  
        Properties properties=new Properties();  
        properties.setProperty("kaptcha.border", "yes");  
        properties.setProperty("kaptcha.border.color", "51,171,160");  
        properties.setProperty("kaptcha.textproducer.font.color", "51,171,160");  
        properties.setProperty("kaptcha.image.width", "125");  
        properties.setProperty("kaptcha.image.height", "45");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        //properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.FishEyeGimpy");
        properties.setProperty("kaptcha.session.key", "code");  
        properties.setProperty("kaptcha.textproducer.char.length", "4");  
        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑");          
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);  
        return defaultKaptcha;  
    }  
}
