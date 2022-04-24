/* 
 * Copyright (c) 2019, V12 Technology Ltd.
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Server Side Public License, version 1,
 * as published by MongoDB, Inc.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Server Side Public License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see 
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.compiler.builder.yamlbuilder;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author gregp
 */
@Slf4j
public class ConfigParser {

    public CreatorConfig parse(String s) {
        log.debug("parsing creator yaml document");
        Yaml beanLoader = new Yaml();
        CreatorConfig cfg = beanLoader.loadAs(s, CreatorConfig.class);
        log.debug("parsed creator yaml document, now validating");
        cfg.validateConfig();
        log.debug("validated successfully");
        if (log.isDebugEnabled()) {
            log.debug(beanLoader.dumpAsMap(cfg));
        }
        return cfg;
    }
}
