import { FilterType } from "./filter-type"

export class PagableRequest {
    filter : string
    filterType : FilterType
    sortDirection : string
    sortColumn : string
    pageIndex : number
    pageSize : number

    /**
     *
     */
     public constructor(obj?: any) {
        this.filter = obj?.filter ?? "";
        this.filterType = obj?.filterType ?? FilterType.NAME_SEARCH;
        this.sortDirection = obj?.sortDirection ?? "";
        this.sortColumn = obj?.sortColumn ?? "";
        this.pageIndex = obj?.pageIndex ?? 0;
        this.pageSize = obj?.pageSize ?? 2;
    }
}
