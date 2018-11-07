package com.geek.pf.log.loader;

import java.util.List;

import com.geek.pf.log.format.MessageFormat;

/**
 * @author jinkai.xu
 *
 * @date 2018/09/01
 *
 */
public interface IMessageLoader {

    List<MessageFormat> load();

    String getType();
}
