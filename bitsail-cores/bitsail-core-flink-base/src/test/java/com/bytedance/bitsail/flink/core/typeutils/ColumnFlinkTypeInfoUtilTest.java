/*
 * Copyright 2022-2023 Bytedance Ltd. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytedance.bitsail.flink.core.typeutils;

import com.bytedance.bitsail.common.model.ColumnInfo;
import com.bytedance.bitsail.common.type.BitSailTypeInfoConverter;
import com.bytedance.bitsail.common.type.TypeInfoConverter;
import com.bytedance.bitsail.flink.core.typeinfo.PrimitiveColumnTypeInfo;

import com.google.common.collect.ImmutableList;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ColumnFlinkTypeInfoUtilTest {

  @Test
  public void testGetRowTypeInformation() {
    TypeInfoConverter converter = new BitSailTypeInfoConverter();
    List<ColumnInfo> columnInfos = ImmutableList.of(
        new ColumnInfo("type_int", "short"),
        new ColumnInfo("type_string", "string"),
        new ColumnInfo("type_double", "double"),
        new ColumnInfo("type_date", "date"),
        new ColumnInfo("type_binary", "binary"),
        new ColumnInfo("type_list", "list<string>"),
        new ColumnInfo("type_map", "map<string,string>")
    );
    RowTypeInfo rowTypeInfo = ColumnFlinkTypeInfoUtil.getRowTypeInformation(converter, columnInfos);

    Assert.assertEquals(PrimitiveColumnTypeInfo.LONG_COLUMN_TYPE_INFO, rowTypeInfo.getTypeAt(0));
    Assert.assertEquals(PrimitiveColumnTypeInfo.STRING_COLUMN_TYPE_INFO, rowTypeInfo.getTypeAt(1));
    Assert.assertEquals(PrimitiveColumnTypeInfo.DOUBLE_COLUMN_TYPE_INFO, rowTypeInfo.getTypeAt(2));
    Assert.assertEquals(PrimitiveColumnTypeInfo.DATE_COLUMN_TYPE_INFO, rowTypeInfo.getTypeAt(3));
    Assert.assertEquals(PrimitiveColumnTypeInfo.BYTES_COLUMN_TYPE_INFO, rowTypeInfo.getTypeAt(4));

    Assert.assertEquals("List<StringColumn>", rowTypeInfo.getTypeAt(5).toString());
    Assert.assertEquals("Map<StringColumn, StringColumn>", rowTypeInfo.getTypeAt(6).toString());
  }
}