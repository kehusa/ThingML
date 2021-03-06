/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.compilers.java.cepHelper;

import org.sintef.thingml.Filter;
import org.sintef.thingml.LengthWindow;
import org.sintef.thingml.TimeWindow;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingCepViewCompiler;

/**
 * @author ludovic
 */
public class JavaCepViewCompiler extends ThingCepViewCompiler{
    @Override
    public void generate(Filter filter, StringBuilder builder, Context context) {
        builder.append(".filter(" + filter.getFilterOp().getOperatorRef().getName() + "())");
    }

    @Override
    public void generate(TimeWindow timeWindow, StringBuilder builder, Context context) {
        builder.append(".buffer(" + timeWindow.getSize() + "," + timeWindow.getStep() + ",TimeUnit.MILLISECONDS)");
    }

    @Override
    public void generate(LengthWindow lengthWindow, StringBuilder builder, Context context) {
        builder.append(".buffer(" + lengthWindow.getNbEvents());
        if(lengthWindow.getStep() != -1) {
            builder.append(", " + lengthWindow.getStep());
        }
        builder.append( ")");
    }
}
