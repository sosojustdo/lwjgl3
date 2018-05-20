/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package vulkan.templates

import org.lwjgl.generator.*
import vulkan.*

val EXT_debug_utils = "EXTDebugUtils".nativeClassVK("EXT_debug_utils", type = "instance", postfix = EXT) {
    documentation =
        """
        Due to the nature of the Vulkan interface, there is very little error information available to the developer and application. By using the {@code VK_EXT_debug_utils} extension, developers <b>can</b> obtain more information. When combined with validation layers, even more detailed feedback on the application's use of Vulkan will be provided.

        This extension provides the following capabilities:

        <ul>
            <li>The ability to create a debug messenger which will pass along debug messages to an application supplied callback.</li>
            <li>The ability to identify specific Vulkan objects using a name or tag to improve tracking.</li>
            <li>The ability to identify specific sections within a {@code VkQueue} or {@code VkCommandBuffer} using labels to aid organization and offline analysis in external tools.</li>
        </ul>

        The main difference between this extension and {@link EXTDebugReport VK_EXT_debug_report} and {@link EXTDebugMarker VK_EXT_debug_marker} is that those extensions use {@code VkDebugReportObjectTypeEXT} to identify objects. This extension uses the core {@code VkObjectType} in place of {@code VkDebugReportObjectTypeEXT}. The primary reason for this move is that no future object type handle enumeration values will be added to {@code VkDebugReportObjectTypeEXT} since the creation of {@code VkObjectType}.

        In addition, this extension combines the functionality of both {@link EXTDebugReport VK_EXT_debug_report} and {@link EXTDebugMarker VK_EXT_debug_marker} by allowing object name and debug markers (now called labels) to be returned to the application's callback function. This should assist in clarifying the details of a debug message including: what objects are involved and potentially which location within a VkQueue or VkCommandBuffer the message occurred.

        <h5>Examples</h5>
        <b>Example 1</b>

        {@code VK_EXT_debug_utils} allows an application to register multiple callbacks with any Vulkan component wishing to report debug information. Some callbacks may log the information to a file, others may cause a debug break point or other application defined behavior. An application <b>can</b> register callbacks even when no validation layers are enabled, but they will only be called for loader and, if implemented, driver events.

        To capture events that occur while creating or destroying an instance an application <b>can</b> link a ##VkDebugUtilsMessengerCreateInfoEXT structure to the {@code pNext} element of the ##VkInstanceCreateInfo structure given to #CreateInstance(). This callback is only valid for the duration of the #CreateInstance() and the #DestroyInstance() call. Use #CreateDebugUtilsMessengerEXT() to create persistent callback objects.

        Example uses: Create three callback objects. One will log errors and warnings to the debug console using Windows {@code OutputDebugString}. The second will cause the debugger to break at that callback when an error happens and the third will log warnings to stdout.

        <code><pre>
￿    extern VkInstance instance;
￿    VkResult res;
￿    VkDebugUtilsMessengerEXT cb1, cb2, cb3;
￿
￿    // Must call extension functions through a function pointer:
￿    PFN_vkCreateDebugUtilsMessengerEXT pfnCreateDebugUtilsMessengerEXT = (PFN_vkCreateDebugUtilsMessengerEXT)vkGetDeviceProcAddr(device, "vkCreateDebugUtilsMessengerEXT");
￿    PFN_vkDestroyDebugUtilsMessengerEXT pfnDestroyDebugUtilsMessengerEXT = (PFN_vkDestroyDebugUtilsMessengerEXT)vkGetDeviceProcAddr(device, "vkDestroyDebugUtilsMessengerEXT");
￿
￿    VkDebugUtilsMessengeCreateInfoEXT callback1 = {
￿            VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT,  // sType
￿            NULL,                                                     // pNext
￿            0,                                                        // flags
￿            VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT |           // messageSeverity
￿            VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT,
￿            VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT |             // messageType
￿            VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT,
￿            myOutputDebugString,                                      // pfnUserCallback
￿            NULL                                                      // pUserData
￿    };
￿    res = pfnCreateDebugUtilsMessengerEXT(instance, &callback1, &cb1);
￿    if (res != VK_SUCCESS) {
￿       // Do error handling for VK_ERROR_OUT_OF_MEMORY
￿    }
￿
￿    callback1.messageSeverity = VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT;
￿    callback1.pfnCallback = myDebugBreak;
￿    callback1.pUserData = NULL;
￿    res = pfnCreateDebugUtilsMessengerEXT(instance, &callback1, &cb2);
￿    if (res != VK_SUCCESS) {
￿       // Do error handling for VK_ERROR_OUT_OF_MEMORY
￿    }
￿
￿    VkDebugUtilsMessengerCreateInfoEXT callback3 = {
￿            VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT,  // sType
￿            NULL,                                                     // pNext
￿            0,                                                        // flags
￿            VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT,          // messageSeverity
￿            VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT |             // messageType
￿            VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT,
￿            mystdOutLogger,                                           // pfnUserCallback
￿            NULL                                                      // pUserData
￿    };
￿    res = pfnCreateDebugUtilsMessengerEXT(instance, &callback3, &cb3);
￿    if (res != VK_SUCCESS) {
￿       // Do error handling for VK_ERROR_OUT_OF_MEMORY
￿    }
￿
￿    ...
￿
￿    // Remove callbacks when cleaning up
￿    pfnDestroyDebugUtilsMessengerEXT(instance, cb1);
￿    pfnDestroyDebugUtilsMessengerEXT(instance, cb2);
￿    pfnDestroyDebugUtilsMessengerEXT(instance, cb3);</pre></code>

        <b>Example 2</b>

        Associate a name with an image, for easier debugging in external tools or with validation layers that can print a friendly name when referring to objects in error messages.

        <code><pre>
￿    extern VkDevice device;
￿    extern VkImage image;
￿
￿    // Must call extension functions through a function pointer:
￿    PFN_vkSetDebugUtilsObjectNameEXT pfnSetDebugUtilsObjectNameEXT = (PFN_vkSetDebugUtilsObjectNameEXT)vkGetDeviceProcAddr(device, "vkSetDebugUtilsObjectNameEXT");
￿
￿    // Set a name on the image
￿    const VkDebugUtilsObjectNameInfoEXT imageNameInfo =
￿    {
￿        VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT, // sType
￿        NULL,                                               // pNext
￿        VK_OBJECT_TYPE_IMAGE,                               // objectType
￿        (uint64_t)image,                                    // object
￿        "Brick Diffuse Texture",                            // pObjectName
￿    };
￿
￿    pfnSetDebugUtilsObjectNameEXT(device, &imageNameInfo);
￿
￿    // A subsequent error might print:
￿    //   Image 'Brick Diffuse Texture' (0xc0dec0dedeadbeef) is used in a
￿    //   command buffer with no memory bound to it.</pre></code>

        <b>Example 3</b>

        Annotating regions of a workload with naming information so that offline analysis tools can display a more usable visualization of the commands submitted.

        <code><pre>
￿    extern VkDevice device;
￿    extern VkCommandBuffer commandBuffer;
￿
￿    // Must call extension functions through a function pointer:
￿    PFN_vkQueueBeginDebugUtilsLabelEXT pfnQueueBeginDebugUtilsLabelEXT = (PFN_vkQueueBeginDebugUtilsLabelEXT)vkGetDeviceProcAddr(device, "vkQueueBeginDebugUtilsLabelEXT");
￿    PFN_vkQueueEndDebugUtilsLabelEXT pfnQueueEndDebugUtilsLabelEXT = (PFN_vkQueueEndDebugUtilsLabelEXT)vkGetDeviceProcAddr(device, "vkQueueEndDebugUtilsLabelEXT");
￿    PFN_vkCmdBeginDebugUtilsLabelEXT pfnCmdBeginDebugUtilsLabelEXT = (PFN_vkCmdBeginDebugUtilsLabelEXT)vkGetDeviceProcAddr(device, "vkCmdBeginDebugUtilsLabelEXT");
￿    PFN_vkCmdEndDebugUtilsLabelEXT pfnCmdEndDebugUtilsLabelEXT = (PFN_vkCmdEndDebugUtilsLabelEXT)vkGetDeviceProcAddr(device, "vkCmdEndDebugUtilsLabelEXT");
￿    PFN_vkCmdInsertDebugUtilsLabelEXT pfnCmdInsertDebugUtilsLabelEXT = (PFN_vkCmdInsertDebugUtilsLabelEXT)vkGetDeviceProcAddr(device, "vkCmdInsertDebugUtilsLabelEXT");
￿
￿    // Describe the area being rendered
￿    const VkDebugUtilsLabelEXT houseLabel =
￿    {
￿        VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT, // sType
￿        NULL,                                    // pNext
￿        "Brick House",                           // pLabelName
￿        { 1.0f, 0.0f, 0.0f, 1.0f },              // color
￿    };
￿
￿    // Start an annotated group of calls under the 'Brick House' name
￿    pfnCmdBeginDebugUtilsLabelEXT(commandBuffer, &houseLabel);
￿    {
￿        // A mutable structure for each part being rendered
￿        VkDebugUtilsLabelEXT housePartLabel =
￿        {
￿            VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT, // sType
￿            NULL,                                    // pNext
￿            NULL,                                    // pLabelName
￿            { 0.0f, 0.0f, 0.0f, 0.0f },              // color
￿        };
￿
￿        // Set the name and insert the marker
￿        housePartLabel.pLabelName = "Walls";
￿        pfnCmdInsertDebugUtilsLabelEXT(commandBuffer, &housePartLabel);
￿
￿        // Insert the drawcall for the walls
￿        vkCmdDrawIndexed(commandBuffer, 1000, 1, 0, 0, 0);
￿
￿        // Insert a recursive region for two sets of windows
￿        housePartLabel.pLabelName = "Windows";
￿        pfnCmdBeginDebugUtilsLabelEXT(commandBuffer, &housePartLabel);
￿        {
￿            vkCmdDrawIndexed(commandBuffer, 75, 6, 1000, 0, 0);
￿            vkCmdDrawIndexed(commandBuffer, 100, 2, 1450, 0, 0);
￿        }
￿        pfnCmdEndDebugUtilsLabelEXT(commandBuffer);
￿
￿        housePartLabel.pLabelName = "Front Door";
￿        pfnCmdInsertDebugUtilsLabelEXT(commandBuffer, &housePartLabel);
￿
￿        vkCmdDrawIndexed(commandBuffer, 350, 1, 1650, 0, 0);
￿
￿        housePartLabel.pLabelName = "Roof";
￿        pfnCmdInsertDebugUtilsLabelEXT(commandBuffer, &housePartLabel);
￿
￿        vkCmdDrawIndexed(commandBuffer, 500, 1, 2000, 0, 0);
￿    }
￿    // End the house annotation started above
￿    pfnCmdEndDebugUtilsLabelEXT(commandBuffer);
￿
￿    // Do other work
￿
￿    vkEndCommandBuffer(commandBuffer);
￿
￿    // Describe the queue being used
￿    const VkDebugUtilsLabelEXT queueLabel =
￿    {
￿        VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT, // sType
￿        NULL,                                    // pNext
￿        "Main Render Work",                      // pLabelName
￿        { 0.0f, 1.0f, 0.0f, 1.0f },              // color
￿    };
￿
￿    // Identify the queue label region
￿    pfnQueueBeginDebugUtilsLabelEXT(queue, &queueLabel);
￿
￿    // Submit the work for the main render thread
￿    const VkCommandBuffer cmd_bufs[] = {commandBuffer};
￿    VkSubmitInfo submit_info = {.sType = VK_STRUCTURE_TYPE_SUBMIT_INFO,
￿                                .pNext = NULL,
￿                                .waitSemaphoreCount = 0,
￿                                .pWaitSemaphores = NULL,
￿                                .pWaitDstStageMask = NULL,
￿                                .commandBufferCount = 1,
￿                                .pCommandBuffers = cmd_bufs,
￿                                .signalSemaphoreCount = 0,
￿                                .pSignalSemaphores = NULL};
￿    vkQueueSubmit(queue, 1, &submit_info, fence);
￿
￿    // End the queue label region
￿    pfnQueueEndDebugUtilsLabelEXT(queue);</pre></code>

        <dl>
            <dt><b>Name String</b></dt>
            <dd>{@code VK_EXT_debug_utils}</dd>

            <dt><b>Extension Type</b></dt>
            <dd>Instance extension</dd>

            <dt><b>Registered Extension Number</b></dt>
            <dd>129</dd>

            <dt><b>Revision</b></dt>
            <dd>1</dd>

            <dt><b>Extension and Version Dependencies</b></dt>
            <dd><ul>
                <li>Requires Vulkan 1.0</li>
            </ul></dd>

            <dt><b>Contact</b></dt>
            <dd><ul>
                <li>Mark Young @marky-lunarg</li>
            </ul></dd>

            <dt><b>Last Modified Date</b></dt>
            <dd>2017-09-14</dd>

            <dt><b>Revision</b></dt>
            <dd>1</dd>

            <dt><b>IP Status</b></dt>
            <dd>No known IP claims.</dd>

            <dt><b>Dependencies</b></dt>
            <dd><ul>
                <li>This extension is written against version 1.0 of the Vulkan API.</li>
                <li>Requires {@code VkObjectType}</li>
            </ul></dd>

            <dt><b>Contributors</b></dt>
            <dd><ul>
                <li>Mark Young, LunarG</li>
                <li>Baldur Karlsson</li>
                <li>Ian Elliott, Google</li>
                <li>Courtney Goeltzenleuchter, Google</li>
                <li>Karl Schultz, LunarG</li>
                <li>Mark Lobodzinski, LunarG</li>
                <li>Mike Schuchardt, LunarG</li>
                <li>Jaakko Konttinen, AMD</li>
                <li>Dan Ginsburg, Valve Software</li>
                <li>Rolando Olivares, Epic Games</li>
                <li>Dan Baker, Oxide Games</li>
                <li>Kyle Spagnoli, NVIDIA</li>
                <li>Jon Ashburn, LunarG</li>
            </ul></dd>
        </dl>
        """

    IntConstant(
        "The extension specification version.",

        "EXT_DEBUG_UTILS_SPEC_VERSION".."1"
    )

    StringConstant(
        "The extension name.",

        "EXT_DEBUG_UTILS_EXTENSION_NAME".."VK_EXT_debug_utils"
    )

    EnumConstant(
        "Extends {@code VkStructureType}.",

        "STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT".."1000128000",
        "STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_TAG_INFO_EXT".."1000128001",
        "STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT".."1000128002",
        "STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT".."1000128003",
        "STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT".."1000128004"
    )

    EnumConstant(
        "Extends {@code VkObjectType}.",

        "OBJECT_TYPE_DEBUG_UTILS_MESSENGER_EXT".."1000128000"
    )

    EnumConstant(
        """
        VkDebugUtilsMessageSeverityFlagBitsEXT - Bitmask specifying which severities of events cause a debug messenger callback

        <h5>Description</h5>
        <ul>
            <li>#DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT specifies the most verbose output indicating all diagnostic messages from the Vulkan loader, layers, and drivers should be captured.</li>
            <li>#DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT specifies an informational message such as resource details that may be handy when debugging an application.</li>
            <li>#DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT specifies use of Vulkan that <b>may</b> expose an app bug. Such cases may not be immediately harmful, such as a fragment shader outputting to a location with no attachment. Other cases <b>may</b> point to behavior that is almost certainly bad when unintended such as using an image whose memory has not been filled. In general if you see a warning but you know that the behavior is intended/desired, then simply ignore the warning.</li>
            <li>#DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT specifies that an error that may cause undefined results, including an application crash.</li>
        </ul>

        <h5>See Also</h5>
        {@code VkDebugUtilsMessageSeverityFlagsEXT}, #SubmitDebugUtilsMessageEXT()
        """,

        "DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT".enum(0x00000001),
        "DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT".enum(0x00000010),
        "DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT".enum(0x00000100),
        "DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT".enum(0x00001000)
    )

    EnumConstant(
        """
        VkDebugUtilsMessageTypeFlagBitsEXT - Bitmask specifying which types of events cause a debug messenger callback

        <h5>Description</h5>
        <ul>
            <li>#DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT specifies that some general event has occurred. This is typically a non-specification, non-performance event.</li>
            <li>#DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT specifies that something has occurred during validation against the Vulkan specification that may indicate invalid behavior.</li>
            <li>#DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT specifies a potentially non-optimal use of Vulkan, e.g. using #CmdClearColorImage() when setting ##VkAttachmentDescription{@code ::loadOp} to #ATTACHMENT_LOAD_OP_CLEAR would have worked.</li>
        </ul>

        <h5>See Also</h5>
        {@code VkDebugUtilsMessageTypeFlagsEXT}
        """,

        "DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT".enum(0x00000001),
        "DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT".enum(0x00000002),
        "DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT".enum(0x00000004)
    )

    VkResult(
        "SetDebugUtilsObjectNameEXT",
        """
        Give a user-friendly name to an object.

        <h5>C Specification</h5>
        <code><pre>
￿VkResult vkSetDebugUtilsObjectNameEXT(
￿    VkDevice                                    device,
￿    const VkDebugUtilsObjectNameInfoEXT*        pNameInfo);</pre></code>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code device} <b>must</b> be a valid {@code VkDevice} handle</li>
            <li>{@code pNameInfo} <b>must</b> be a valid pointer to a valid ##VkDebugUtilsObjectNameInfoEXT structure</li>
        </ul>

        <h5>Host Synchronization</h5>
        <ul>
            <li>Host access to {@code pNameInfo.objectHandle} <b>must</b> be externally synchronized</li>
        </ul>

        <h5>Return Codes</h5>
        <dl>
            <dt>On success, this command returns</dt>
            <dd><ul>
                <li>#SUCCESS</li>
            </ul></dd>

            <dt>On failure, this command returns</dt>
            <dd><ul>
                <li>#ERROR_OUT_OF_HOST_MEMORY</li>
                <li>#ERROR_OUT_OF_DEVICE_MEMORY</li>
            </ul></dd>
        </dl>

        <h5>See Also</h5>
        ##VkDebugUtilsObjectNameInfoEXT
        """,

        VkDevice.IN("device", "the device that created the object."),
        VkDebugUtilsObjectNameInfoEXT.const.p.IN("pNameInfo", "a pointer to an instance of the ##VkDebugUtilsObjectNameInfoEXT structure specifying the parameters of the name to set on the object.")
    )

    VkResult(
        "SetDebugUtilsObjectTagEXT",
        """
        Attach arbitrary data to an object.

        <h5>C Specification</h5>
        <code><pre>
￿VkResult vkSetDebugUtilsObjectTagEXT(
￿    VkDevice                                    device,
￿    const VkDebugUtilsObjectTagInfoEXT*         pTagInfo);</pre></code>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code device} <b>must</b> be a valid {@code VkDevice} handle</li>
            <li>{@code pTagInfo} <b>must</b> be a valid pointer to a valid ##VkDebugUtilsObjectTagInfoEXT structure</li>
        </ul>

        <h5>Host Synchronization</h5>
        <ul>
            <li>Host access to {@code pTagInfo.objectHandle} <b>must</b> be externally synchronized</li>
        </ul>

        <h5>Return Codes</h5>
        <dl>
            <dt>On success, this command returns</dt>
            <dd><ul>
                <li>#SUCCESS</li>
            </ul></dd>

            <dt>On failure, this command returns</dt>
            <dd><ul>
                <li>#ERROR_OUT_OF_HOST_MEMORY</li>
                <li>#ERROR_OUT_OF_DEVICE_MEMORY</li>
            </ul></dd>
        </dl>

        <h5>See Also</h5>
        ##VkDebugUtilsObjectTagInfoEXT
        """,

        VkDevice.IN("device", "the device that created the object."),
        VkDebugUtilsObjectTagInfoEXT.const.p.IN("pTagInfo", "a pointer to an instance of the ##VkDebugUtilsObjectTagInfoEXT structure specifying the parameters of the tag to attach to the object.")
    )

    void(
        "QueueBeginDebugUtilsLabelEXT",
        """
        Open a queue debug label region.

        <h5>C Specification</h5>
        A queue debug label region is opened by calling:

        <code><pre>
￿void vkQueueBeginDebugUtilsLabelEXT(
￿    VkQueue                                     queue,
￿    const VkDebugUtilsLabelEXT*                 pLabelInfo);</pre></code>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code queue} <b>must</b> be a valid {@code VkQueue} handle</li>
            <li>{@code pLabelInfo} <b>must</b> be a valid pointer to a valid ##VkDebugUtilsLabelEXT structure</li>
        </ul>

        <h5>Command Properties</h5>
        <table class="lwjgl">
            <thead><tr><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkCommandBufferLevel">Command Buffer Levels</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#vkCmdBeginRenderPass">Render Pass Scope</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkQueueFlagBits">Supported Queue Types</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#synchronization-pipeline-stages-types">Pipeline Type</a></th></tr></thead>
            <tbody><tr><td>-</td><td>-</td><td>Any</td><td>-</td></tr></tbody>
        </table>

        <h5>See Also</h5>
        ##VkDebugUtilsLabelEXT
        """,

        VkQueue.IN("queue", "the queue in which to start a debug label region."),
        VkDebugUtilsLabelEXT.const.p.IN("pLabelInfo", "a pointer to an instance of the ##VkDebugUtilsLabelEXT structure specifying the parameters of the label region to open.")
    )

    void(
        "QueueEndDebugUtilsLabelEXT",
        """
        Close a queue debug label region.

        <h5>C Specification</h5>
        A queue debug label region is closed by calling:

        <code><pre>
￿void vkQueueEndDebugUtilsLabelEXT(
￿    VkQueue                                     queue);</pre></code>

        <h5>Description</h5>
        The calls to #QueueBeginDebugUtilsLabelEXT() and #QueueEndDebugUtilsLabelEXT() <b>must</b> be matched and balanced.

        <h5>Valid Usage</h5>
        <ul>
            <li>There <b>must</b> be an outstanding #QueueBeginDebugUtilsLabelEXT() command prior to the #QueueEndDebugUtilsLabelEXT() on the queue</li>
        </ul>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code queue} <b>must</b> be a valid {@code VkQueue} handle</li>
        </ul>

        <h5>Command Properties</h5>
        <table class="lwjgl">
            <thead><tr><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkCommandBufferLevel">Command Buffer Levels</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#vkCmdBeginRenderPass">Render Pass Scope</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkQueueFlagBits">Supported Queue Types</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#synchronization-pipeline-stages-types">Pipeline Type</a></th></tr></thead>
            <tbody><tr><td>-</td><td>-</td><td>Any</td><td>-</td></tr></tbody>
        </table>
        """,

        VkQueue.IN("queue", "the queue in which a debug label region should be closed.")
    )

    void(
        "QueueInsertDebugUtilsLabelEXT",
        """
        Insert a label into a queue.

        <h5>C Specification</h5>
        A single label can be inserted into a queue by calling:

        <code><pre>
￿void vkQueueInsertDebugUtilsLabelEXT(
￿    VkQueue                                     queue,
￿    const VkDebugUtilsLabelEXT*                 pLabelInfo);</pre></code>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code queue} <b>must</b> be a valid {@code VkQueue} handle</li>
            <li>{@code pLabelInfo} <b>must</b> be a valid pointer to a valid ##VkDebugUtilsLabelEXT structure</li>
        </ul>

        <h5>Command Properties</h5>
        <table class="lwjgl">
            <thead><tr><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkCommandBufferLevel">Command Buffer Levels</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#vkCmdBeginRenderPass">Render Pass Scope</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkQueueFlagBits">Supported Queue Types</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#synchronization-pipeline-stages-types">Pipeline Type</a></th></tr></thead>
            <tbody><tr><td>-</td><td>-</td><td>Any</td><td>-</td></tr></tbody>
        </table>

        <h5>See Also</h5>
        ##VkDebugUtilsLabelEXT
        """,

        VkQueue.IN("queue", "the queue into which a debug label will be inserted."),
        VkDebugUtilsLabelEXT.const.p.IN("pLabelInfo", "a pointer to an instance of the ##VkDebugUtilsLabelEXT structure specifying the parameters of the label to insert.")
    )

    void(
        "CmdBeginDebugUtilsLabelEXT",
        """
        Open a command buffer debug label region.

        <h5>C Specification</h5>
        A command buffer debug label region can be opened by calling:

        <code><pre>
￿void vkCmdBeginDebugUtilsLabelEXT(
￿    VkCommandBuffer                             commandBuffer,
￿    const VkDebugUtilsLabelEXT*                 pLabelInfo);</pre></code>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code commandBuffer} <b>must</b> be a valid {@code VkCommandBuffer} handle</li>
            <li>{@code pLabelInfo} <b>must</b> be a valid pointer to a valid ##VkDebugUtilsLabelEXT structure</li>
            <li>{@code commandBuffer} <b>must</b> be in the <a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#commandbuffers-lifecycle">recording state</a></li>
            <li>The {@code VkCommandPool} that {@code commandBuffer} was allocated from <b>must</b> support graphics, or compute operations</li>
        </ul>

        <h5>Host Synchronization</h5>
        <ul>
            <li>Host access to the {@code VkCommandPool} that {@code commandBuffer} was allocated from <b>must</b> be externally synchronized</li>
        </ul>

        <h5>Command Properties</h5>
        <table class="lwjgl">
            <thead><tr><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkCommandBufferLevel">Command Buffer Levels</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#vkCmdBeginRenderPass">Render Pass Scope</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkQueueFlagBits">Supported Queue Types</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#synchronization-pipeline-stages-types">Pipeline Type</a></th></tr></thead>
            <tbody><tr><td>Primary Secondary</td><td>Both</td><td>Graphics Compute</td><td></td></tr></tbody>
        </table>

        <h5>See Also</h5>
        ##VkDebugUtilsLabelEXT
        """,

        VkCommandBuffer.IN("commandBuffer", "the command buffer into which the command is recorded."),
        VkDebugUtilsLabelEXT.const.p.IN("pLabelInfo", "a pointer to an instance of the ##VkDebugUtilsLabelEXT structure specifying the parameters of the label region to open.")
    )

    void(
        "CmdEndDebugUtilsLabelEXT",
        """
        Close a command buffer label region.

        <h5>C Specification</h5>
        A command buffer label region can be closed by calling:

        <code><pre>
￿void vkCmdEndDebugUtilsLabelEXT(
￿    VkCommandBuffer                             commandBuffer);</pre></code>

        <h5>Description</h5>
        An application <b>may</b> open a debug label region in one command buffer and close it in another, or otherwise split debug label regions across multiple command buffers or multiple queue submissions. When viewed from the linear series of submissions to a single queue, the calls to #CmdBeginDebugUtilsLabelEXT() and #CmdEndDebugUtilsLabelEXT() <b>must</b> be matched and balanced.

        <h5>Valid Usage</h5>
        <ul>
            <li>There <b>must</b> be an outstanding #CmdBeginDebugUtilsLabelEXT() command prior to the #CmdEndDebugUtilsLabelEXT() on the queue that {@code commandBuffer} is submitted to</li>
            <li>If {@code commandBuffer} is a secondary command buffer, there <b>must</b> be an outstanding #CmdBeginDebugUtilsLabelEXT() command recorded to {@code commandBuffer} that has not previously been ended by a call to #CmdEndDebugUtilsLabelEXT().</li>
        </ul>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code commandBuffer} <b>must</b> be a valid {@code VkCommandBuffer} handle</li>
            <li>{@code commandBuffer} <b>must</b> be in the <a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#commandbuffers-lifecycle">recording state</a></li>
            <li>The {@code VkCommandPool} that {@code commandBuffer} was allocated from <b>must</b> support graphics, or compute operations</li>
        </ul>

        <h5>Host Synchronization</h5>
        <ul>
            <li>Host access to the {@code VkCommandPool} that {@code commandBuffer} was allocated from <b>must</b> be externally synchronized</li>
        </ul>

        <h5>Command Properties</h5>
        <table class="lwjgl">
            <thead><tr><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkCommandBufferLevel">Command Buffer Levels</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#vkCmdBeginRenderPass">Render Pass Scope</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkQueueFlagBits">Supported Queue Types</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#synchronization-pipeline-stages-types">Pipeline Type</a></th></tr></thead>
            <tbody><tr><td>Primary Secondary</td><td>Both</td><td>Graphics Compute</td><td></td></tr></tbody>
        </table>
        """,

        VkCommandBuffer.IN("commandBuffer", "the command buffer into which the command is recorded.")
    )

    void(
        "CmdInsertDebugUtilsLabelEXT",
        """
        Insert a label into a command buffer.

        <h5>C Specification</h5>
        A single debug label can be inserted into a command buffer by calling:

        <code><pre>
￿void vkCmdInsertDebugUtilsLabelEXT(
￿    VkCommandBuffer                             commandBuffer,
￿    const VkDebugUtilsLabelEXT*                 pLabelInfo);</pre></code>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code commandBuffer} <b>must</b> be a valid {@code VkCommandBuffer} handle</li>
            <li>{@code pLabelInfo} <b>must</b> be a valid pointer to a valid ##VkDebugUtilsLabelEXT structure</li>
            <li>{@code commandBuffer} <b>must</b> be in the <a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#commandbuffers-lifecycle">recording state</a></li>
            <li>The {@code VkCommandPool} that {@code commandBuffer} was allocated from <b>must</b> support graphics, or compute operations</li>
        </ul>

        <h5>Host Synchronization</h5>
        <ul>
            <li>Host access to the {@code VkCommandPool} that {@code commandBuffer} was allocated from <b>must</b> be externally synchronized</li>
        </ul>

        <h5>Command Properties</h5>
        <table class="lwjgl">
            <thead><tr><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkCommandBufferLevel">Command Buffer Levels</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#vkCmdBeginRenderPass">Render Pass Scope</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#VkQueueFlagBits">Supported Queue Types</a></th><th><a target="_blank" href="https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\#synchronization-pipeline-stages-types">Pipeline Type</a></th></tr></thead>
            <tbody><tr><td>Primary Secondary</td><td>Both</td><td>Graphics Compute</td><td></td></tr></tbody>
        </table>

        <h5>See Also</h5>
        ##VkDebugUtilsLabelEXT
        """,

        VkCommandBuffer.IN("commandBuffer", "the command buffer into which the command is recorded."),
        VkDebugUtilsLabelEXT.const.p.IN("pLabelInfo", "")
    )

    VkResult(
        "CreateDebugUtilsMessengerEXT",
        """
        Create a debug messenger object.

        <h5>C Specification</h5>
        A debug messenger triggers a debug callback with a debug message when an event of interest occurs. To create a debug messenger which will trigger a debug callback, call:

        <code><pre>
￿VkResult vkCreateDebugUtilsMessengerEXT(
￿    VkInstance                                  instance,
￿    const VkDebugUtilsMessengerCreateInfoEXT*   pCreateInfo,
￿    const VkAllocationCallbacks*                pAllocator,
￿    VkDebugUtilsMessengerEXT*                   pMessenger);</pre></code>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code instance} <b>must</b> be a valid {@code VkInstance} handle</li>
            <li>{@code pCreateInfo} <b>must</b> be a valid pointer to a valid ##VkDebugUtilsMessengerCreateInfoEXT structure</li>
            <li>If {@code pAllocator} is not {@code NULL}, {@code pAllocator} <b>must</b> be a valid pointer to a valid ##VkAllocationCallbacks structure</li>
            <li>{@code pMessenger} <b>must</b> be a valid pointer to a {@code VkDebugUtilsMessengerEXT} handle</li>
        </ul>

        <h5>Return Codes</h5>
        <dl>
            <dt>On success, this command returns</dt>
            <dd><ul>
                <li>#SUCCESS</li>
            </ul></dd>

            <dt>On failure, this command returns</dt>
            <dd><ul>
                <li>#ERROR_OUT_OF_HOST_MEMORY</li>
            </ul></dd>
        </dl>

        <h5>See Also</h5>
        ##VkAllocationCallbacks, ##VkDebugUtilsMessengerCreateInfoEXT
        """,

        VkInstance.IN("instance", "the instance the messenger will be used with."),
        VkDebugUtilsMessengerCreateInfoEXT.const.p.IN("pCreateInfo", "points to a ##VkDebugUtilsMessengerCreateInfoEXT structure which contains the callback pointer as well as defines the conditions under which this messenger will trigger the callback."),
        nullable..VkAllocationCallbacks.const.p.IN("pAllocator", "controls host memory allocation as described in the <a target=\"_blank\" href=\"https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\\#memory-allocation\">Memory Allocation</a> chapter."),
        Check(1)..VkDebugUtilsMessengerEXT.p.OUT("pMessenger", "a pointer to record the {@code VkDebugUtilsMessengerEXT} object created.")
    )

    void(
        "DestroyDebugUtilsMessengerEXT",
        """
        Destroy a debug messenger object.

        <h5>C Specification</h5>
        To destroy a {@code VkDebugUtilsMessengerEXT} object, call:

        <code><pre>
￿void vkDestroyDebugUtilsMessengerEXT(
￿    VkInstance                                  instance,
￿    VkDebugUtilsMessengerEXT                    messenger,
￿    const VkAllocationCallbacks*                pAllocator);</pre></code>

        <h5>Valid Usage</h5>
        <ul>
            <li>If ##VkAllocationCallbacks were provided when {@code messenger} was created, a compatible set of callbacks <b>must</b> be provided here</li>
            <li>If no ##VkAllocationCallbacks were provided when {@code messenger} was created, {@code pAllocator} <b>must</b> be {@code NULL}</li>
        </ul>

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code instance} <b>must</b> be a valid {@code VkInstance} handle</li>
            <li>{@code messenger} <b>must</b> be a valid {@code VkDebugUtilsMessengerEXT} handle</li>
            <li>If {@code pAllocator} is not {@code NULL}, {@code pAllocator} <b>must</b> be a valid pointer to a valid ##VkAllocationCallbacks structure</li>
            <li>{@code messenger} <b>must</b> have been created, allocated, or retrieved from {@code instance}</li>
        </ul>

        <h5>Host Synchronization</h5>
        <ul>
            <li>Host access to {@code messenger} <b>must</b> be externally synchronized</li>
        </ul>

        <h5>See Also</h5>
        ##VkAllocationCallbacks
        """,

        VkInstance.IN("instance", "the instance where the callback was created."),
        VkDebugUtilsMessengerEXT.IN("messenger", "the {@code VkDebugUtilsMessengerEXT} object to destroy. {@code messenger} is an externally synchronized object and <b>must</b> not be used on more than one thread at a time. This means that #DestroyDebugUtilsMessengerEXT() <b>must</b> not be called when a callback is active."),
        nullable..VkAllocationCallbacks.const.p.IN("pAllocator", "controls host memory allocation as described in the <a target=\"_blank\" href=\"https://www.khronos.org/registry/vulkan/specs/1.0-extensions/html/vkspec.html\\#memory-allocation\">Memory Allocation</a> chapter.")
    )

    void(
        "SubmitDebugUtilsMessageEXT",
        """
        Inject a message into a debug stream.

        <h5>C Specification</h5>
        There may be times that a user wishes to intentionally submit a debug message. To do this, call:

        <code><pre>
￿void vkSubmitDebugUtilsMessageEXT(
￿    VkInstance                                  instance,
￿    VkDebugUtilsMessageSeverityFlagBitsEXT      messageSeverity,
￿    VkDebugUtilsMessageTypeFlagsEXT             messageTypes,
￿    const VkDebugUtilsMessengerCallbackDataEXT* pCallbackData);</pre></code>

        <h5>Description</h5>
        The call will propagate through the layers and generate callback(s) as indicated by the message's flags. The parameters are passed on to the callback in addition to the {@code pUserData} value that was defined at the time the messenger was registered.

        <h5>Valid Usage (Implicit)</h5>
        <ul>
            <li>{@code instance} <b>must</b> be a valid {@code VkInstance} handle</li>
            <li>{@code messageSeverity} <b>must</b> be a valid {@code VkDebugUtilsMessageSeverityFlagBitsEXT} value</li>
            <li>{@code messageTypes} <b>must</b> be a valid combination of {@code VkDebugUtilsMessageTypeFlagBitsEXT} values</li>
            <li>{@code messageTypes} <b>must</b> not be 0</li>
            <li>{@code pCallbackData} <b>must</b> be a valid pointer to a valid ##VkDebugUtilsMessengerCallbackDataEXT structure</li>
        </ul>

        <h5>See Also</h5>
        ##VkDebugUtilsMessengerCallbackDataEXT
        """,

        VkInstance.IN("instance", "the debug stream&#8217;s {@code VkInstance}."),
        VkDebugUtilsMessageSeverityFlagBitsEXT.IN("messageSeverity", "the {@code VkDebugUtilsMessageSeverityFlagBitsEXT} severity of this event/message."),
        VkDebugUtilsMessageTypeFlagsEXT.IN("messageTypes", "a bitmask of {@code VkDebugUtilsMessageTypeFlagBitsEXT} specifying which type of event(s) to identify with this message."),
        VkDebugUtilsMessengerCallbackDataEXT.const.p.IN("pCallbackData", "contains all the callback related data in the ##VkDebugUtilsMessengerCallbackDataEXT structure.")
    )
}