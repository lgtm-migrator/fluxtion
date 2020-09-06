/*
 * Copyright (c) 2020, V12 Technology Ltd.
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
package com.fluxtion.integration.etl;

import com.fluxtion.generator.compiler.OutputRegistry;
import java.io.IOException;
import java.io.StringReader;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Greg Higgins greg.higgins@v12technology.com
 */
public class PipelinePersistTest {

    @Test
    @Ignore
    public void writeAndReadPipelines() throws IOException {
        Main main = new Main().start();
        String yaml = ""
                + "id: org.greg.Data2\n"
                + "columns:\n"
                + "- {name: age, type: int}\n"
                + "- {name: lastName, type: String, function: 'return input.toString().toUpperCase();' }\n"
                + "derived:\n"
                + "- {name: halfAge, type: int, function: '"
                + "//some comments\n\n"
                + "return age * 2;'}\n"
                + "postRecordFunction: '//no-op demo callback\n'"
                + "";
        main.buildModel(yaml);
    }
    
    @Test
    @Ignore
    public void runPipeline(){
        Main main = new Main().start();
        String id = "org.greg.Data1";
        StringReader reader = new StringReader("age,f__NAME,lastName\n"
                + "22,feed,fgggf\n"
                + "30AAA000,tim,hfd8e\n"
                + "80,mary,blythe\n"
        );
        main.executePipeline(id, reader);
    }
}