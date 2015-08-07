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
package org.thingml.compilers.javascript;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.CepCompiler;
import org.thingml.compilers.Context;
import org.thingml.compilers.javascript.cepHelper.JSCepViewCompiler;
import org.thingml.compilers.javascript.cepHelper.JSGenerateSourceDeclaration;

/**
 * @author ludovic
 */
public class JSCepCompiler extends CepCompiler {
    private JSCepViewCompiler jsCepViewCompiler = new JSCepViewCompiler();

    public JSCepViewCompiler getJsCepViewCompiler() {
        return jsCepViewCompiler;
    }

    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        JSGenerateSourceDeclaration.generate(stream, stream.getInput(), builder, ctx);
        if(stream.getInput() instanceof SimpleSource) {
            SimpleSource simpleSource = (SimpleSource) stream.getInput();
            String paramName = simpleSource.getMessage().getMessage().getName();
            generateSimpleMergeStreamSubscription(stream, builder, ctx, paramName);
        } else if(stream.getInput() instanceof SourceComposition) {
            generateSimpleMergeStreamSubscription(stream, builder, ctx, "x");
        } else {
            throw UnsupportedException.sourceException(stream.getClass().getName());
        }

    }

    public static void generateSimpleMergeStreamSubscription(Stream stream, StringBuilder builder, Context context, String paramName) {
        builder.append(stream.getInput().qname("_") + ".subscribe(\n")
                .append("function( " + paramName + ") { \n");
        context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);
        builder.append("\t});\n");
    }
}
