<div class="prevNextIcons">
        <div class="control_icons" id="loadPrevIcons">
                <#if hasPrev>
                        <a id="loadPrev" class="next_prev flex align-center" href="${prevPageLink!''}"> << Previous page </a>
                <#else>
                        <span class="next_prev flex align-center">Previous page </span>
                </#if>
        </div>
        <div class="control_icons" id="loadNextIcons">
                <#if hasNext>
                        <a id="loadNext" class="next_prev flex align-center" href="${nextPageLink!''}" data-default-page-size="${defaultPageSize!5}"> Next page >></a>
                <#else>
                        <span class="next_prev flex align-center"> Next page </span>
                </#if>
        </div>
</div>