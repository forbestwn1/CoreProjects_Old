(function framework7ComponentLoader(e,t){void 0===t&&(t=!0);document,window;var a=e.$,n=(e.Template7,e.utils),r=(e.device,e.support,e.Class),o=(e.Modal,e.ConstructorMethods),i=(e.ModalMethods,function(e){function t(t,r){void 0===r&&(r={}),e.call(this,r,[t]);var o,i,s=this;if(s.params=n.extend({},t.params.calendar,r),s.params.containerEl&&0===(o=a(s.params.containerEl)).length)return s;s.params.inputEl&&(i=a(s.params.inputEl));var l="horizontal"===s.params.direction,c=1;l&&(c=t.rtl?-1:1),n.extend(s,{app:t,$containerEl:o,containerEl:o&&o[0],inline:o&&o.length>0,$inputEl:i,inputEl:i&&i[0],initialized:!1,opened:!1,url:s.params.url,isHorizontal:l,inverter:c,animating:!1,hasTimePicker:s.params.timePicker&&!s.params.rangePicker&&!s.params.multiple}),s.dayFormatter=new Intl.DateTimeFormat(s.params.locale,{day:"numeric"}),s.monthFormatter=new Intl.DateTimeFormat(s.params.locale,{month:"long"}),s.yearFormatter=new Intl.DateTimeFormat(s.params.locale,{year:"numeric"}),s.timeSelectorFormatter=new Intl.DateTimeFormat(s.params.locale,s.params.timePickerFormat);var d=s.params,h=d.monthNames,m=d.monthNamesShort,p=d.dayNames,u=d.dayNamesShort,v=s.getIntlNames(),f=v.monthNamesIntl,g=v.monthNamesShortIntl,y=v.dayNamesIntl,k=v.dayNamesShortIntl;function M(){s.open()}function D(e){e.preventDefault()}function w(){s.setValue([]),s.opened&&s.update()}function T(e){var t=a(e.target);!s.destroyed&&s.params&&(s.isPopover()||s.opened&&!s.closing&&(t.closest('[class*="backdrop"]').length||(i&&i.length>0?t[0]!==i[0]&&0===t.closest(".sheet-modal, .calendar-modal").length&&s.close():0===a(e.target).closest(".sheet-modal, .calendar-modal").length&&s.close())))}return"auto"===h&&(h=f),"auto"===m&&(m=g),"auto"===p&&(p=y),"auto"===u&&(u=k),n.extend(s,{monthNames:h,monthNamesShort:m,dayNames:p,dayNamesShort:u}),n.extend(s,{attachInputEvents:function(){s.$inputEl.on("click",M),s.$inputEl.on("input:clear",w),s.params.inputReadOnly&&s.$inputEl.on("focus mousedown",D)},detachInputEvents:function(){s.$inputEl.off("click",M),s.$inputEl.off("input:clear",w),s.params.inputReadOnly&&s.$inputEl.off("focus mousedown",D)},attachHtmlEvents:function(){t.on("click",T)},detachHtmlEvents:function(){t.off("click",T)}}),s.attachCalendarEvents=function(){var e,n,r,o,i,l,c,d,h,m,p,u,v,f=!0,g=s.$el,y=s.$wrapperEl;function k(t){n||e||(e=!0,r="touchstart"===t.type?t.targetTouches[0].pageX:t.pageX,i=r,o="touchstart"===t.type?t.targetTouches[0].pageY:t.pageY,l=o,c=(new Date).getTime(),p=0,f=!0,v=void 0,d=s.monthsTranslate)}function M(t){if(e){var a=s.isHorizontal;i="touchmove"===t.type?t.targetTouches[0].pageX:t.pageX,l="touchmove"===t.type?t.targetTouches[0].pageY:t.pageY,void 0===v&&(v=!!(v||Math.abs(l-o)>Math.abs(i-r))),a&&v?e=!1:(t.preventDefault(),s.animating?e=!1:(f=!1,n||(n=!0,h=y[0].offsetWidth,m=y[0].offsetHeight,y.transition(0)),p=(u=a?i-r:l-o)/(a?h:m),d=100*(s.monthsTranslate*s.inverter+p),y.transform("translate3d("+(a?d:0)+"%, "+(a?0:d)+"%, 0)")))}}function D(){if(!e||!n)return e=!1,void(n=!1);e=!1,n=!1,(new Date).getTime()-c<300?Math.abs(u)<10?s.resetMonth():u>=10?t.rtl?s.nextMonth():s.prevMonth():t.rtl?s.prevMonth():s.nextMonth():p<=-.5?t.rtl?s.prevMonth():s.nextMonth():p>=.5?t.rtl?s.nextMonth():s.prevMonth():s.resetMonth(),setTimeout((function(){f=!0}),100)}function w(e){if(f){var t=a(e.target).parents(".calendar-day");if(0===t.length&&a(e.target).hasClass("calendar-day")&&(t=a(e.target)),0!==t.length&&!t.hasClass("calendar-day-disabled")){s.params.rangePicker||(t.hasClass("calendar-day-next")&&s.nextMonth(),t.hasClass("calendar-day-prev")&&s.prevMonth());var n=parseInt(t.attr("data-year"),10),r=parseInt(t.attr("data-month"),10),o=parseInt(t.attr("data-day"),10);if(s.emit("local::dayClick calendarDayClick",s,t[0],n,r,o),!t.hasClass("calendar-day-selected")||s.params.multiple||s.params.rangePicker){var i=new Date(n,r,o,0,0,0);s.hasTimePicker&&(s.value&&s.value[0]?i.setHours(s.value[0].getHours(),s.value[0].getMinutes()):i.setHours((new Date).getHours(),(new Date).getMinutes())),s.addValue(i)}s.params.closeOnSelect&&(s.params.rangePicker&&2===s.value.length||!s.params.rangePicker)&&s.close()}}}function T(){s.nextMonth()}function $(){s.prevMonth()}function C(){s.nextYear()}function P(){s.prevYear()}function x(){g.append(s.renderMonthPicker())}function E(){var e=a(this);if(e.hasClass("calendar-month-picker-item-current"))g.find(".calendar-month-picker").remove();else{g.find(".calendar-month-picker-item-current").add(e).toggleClass("calendar-month-picker-item-current");var t=e.index()-(parseInt(s.$el.find(".calendar-month-current").attr("data-locale-month"),10)-s.currentMonth);s.setYearMonth(s.currentYear,t,0),setTimeout((function(){g.find(".calendar-month-picker").remove()}),200)}}function I(){g.append(s.renderYearPicker());var e=g.find(".calendar-year-picker-item-current"),t=g.find(".calendar-year-picker");e&&e.length&&t.scrollTop(e[0].offsetTop-t[0].offsetHeight/2+e[0].offsetHeight/2)}function b(){var e=a(this);if(e.hasClass("calendar-year-picker-item-current"))g.find(".calendar-year-picker").remove();else{g.find(".calendar-year-picker-item-current").add(e).toggleClass("calendar-year-picker-item-current");var t=parseInt(e.attr("data-year"),10);s.setYearMonth(t,void 0,0),setTimeout((function(){g.find(".calendar-year-picker").remove()}),200)}}function Y(){s.openTimePicker()}function H(){s.closeTimePicker()}var S=!("touchstart"!==t.touchEvents.start||!t.support.passiveListener)&&{passive:!0,capture:!1};g.find(".calendar-prev-month-button").on("click",$),g.find(".calendar-next-month-button").on("click",T),g.find(".calendar-prev-year-button").on("click",P),g.find(".calendar-next-year-button").on("click",C),s.params.monthPicker&&(g.find(".current-month-value").on("click",x),g.on("click",".calendar-month-picker-item",E)),s.params.yearPicker&&(g.find(".current-year-value").on("click",I),g.on("click",".calendar-year-picker-item",b)),s.hasTimePicker&&(g.find(".calendar-time-selector a").on("click",Y),g.on("click",".calendar-time-picker-close",H)),y.on("click",w),s.params.touchMove&&(y.on(t.touchEvents.start,k,S),t.on("touchmove:active",M),t.on("touchend:passive",D)),s.detachCalendarEvents=function(){g.find(".calendar-prev-month-button").off("click",$),g.find(".calendar-next-month-button").off("click",T),g.find(".calendar-prev-year-button").off("click",P),g.find(".calendar-next-year-button").off("click",C),s.params.monthPicker&&(g.find(".current-month-value").off("click",x),g.off("click",".calendar-month-picker-item",E)),s.params.yearPicker&&(g.find(".current-year-value").off("click",I),g.off("click",".calendar-year-picker-item",b)),s.hasTimePicker&&(g.find(".calendar-time-selector a").off("click",Y),g.off("click",".calendar-time-picker-close",H)),y.off("click",w),s.params.touchMove&&(y.off(t.touchEvents.start,k,S),t.off("touchmove:active",M),t.off("touchend:passive",D))}},s.init(),s}e&&(t.__proto__=e),t.prototype=Object.create(e&&e.prototype),t.prototype.constructor=t;var r={view:{configurable:!0}};return r.view.get=function(){var e,t=this.$inputEl,a=this.app,n=this.params;return n.view?e=n.view:t&&(e=t.parents(".view").length&&t.parents(".view")[0].f7View),e||(e=a.views.main),e},t.prototype.getIntlNames=function(){for(var e,t,a,n=this.params.locale,r=[],o=[],i=[],s=[],l=new Intl.DateTimeFormat(n,{month:"long"}),c=new Intl.DateTimeFormat(n,{month:"short"}),d=new Intl.DateTimeFormat(n,{weekday:"long"}),h=new Intl.DateTimeFormat(n,{weekday:"short"}),m=0;m<24;m+=1){var p=(new Date).setMonth(m,1),u=this.yearFormatter.format(p);e&&u!==e&&(t&&(a=!0),t=!0,e=u),e||(e=u),t&&e===u&&!a&&(r.push(l.format(p)),o.push(c.format(p)))}for(var v=(new Date).getDay(),f=0;f<7;f+=1){var g=(new Date).getTime()+24*(f-v)*60*60*1e3;i.push(d.format(g)),s.push(h.format(g))}return{monthNamesIntl:r,monthNamesShortIntl:o,dayNamesIntl:i,dayNamesShortIntl:s}},t.prototype.normalizeDate=function(e){var t=new Date(e);return this.hasTimePicker?new Date(t.getFullYear(),t.getMonth(),t.getDate(),t.getHours(),t.getMinutes()):new Date(t.getFullYear(),t.getMonth(),t.getDate())},t.prototype.normalizeValues=function(e){var t=this,a=[];return e&&Array.isArray(e)&&(a=e.map((function(e){return t.normalizeDate(e)}))),a},t.prototype.initInput=function(){this.$inputEl&&this.params.inputReadOnly&&this.$inputEl.prop("readOnly",!0)},t.prototype.isPopover=function(){var e=this.app,t=this.modal,a=this.params;if("sheet"===a.openIn)return!1;if(t&&"popover"!==t.type)return!1;if(!this.inline&&this.inputEl){if("popover"===a.openIn)return!0;if(e.device.ios)return!!e.device.ipad;if(e.width>=768)return!0;if(e.device.desktop&&"aurora"===e.theme)return!0}return!1},t.prototype.formatDate=function(e){var t=new Date(e),a=t.getFullYear(),n=t.getMonth(),r=n+1,o=t.getDate(),i=t.getDay(),s=this.monthNames,l=this.monthNamesShort,c=this.dayNames,d=this.dayNamesShort,h=this.params,m=h.dateFormat,p=h.locale;function u(e){return e<10?"0"+e:e}if("string"==typeof m){var v={yyyy:a,yy:String(a).substring(2),mm:u(r),m:r,MM:s[n],M:l[n],dd:u(o),d:o,DD:c[i],D:d[i]};if(this.params.timePicker){var f=t.getHours(),g=t.getMinutes(),y=t.getSeconds(),k=f;f>12&&(k=f-12),0===f&&(k=12);var M=f>=12&&0!==f?"pm":"am";Object.assign(v,{HH:u(f),H:f,hh:u(k),h:k,ss:u(y),s:y,":mm":u(g),":m":g,a:M,A:M.toUpperCase()})}var D=new RegExp(Object.keys(v).map((function(e){return"("+e+")"})).join("|"),"g");return m.replace(D,(function(e){return e in v?v[e]:e}))}return"function"==typeof m?m(t):new Intl.DateTimeFormat(p,m).format(t)},t.prototype.formatValue=function(){var e=this,t=e.value;return e.params.formatValue?e.params.formatValue.call(e,t):t.map((function(t){return e.formatDate(t)})).join(e.params.rangePicker?" - ":", ")},t.prototype.addValue=function(e){var t=this.params,a=t.multiple,n=t.rangePicker,r=t.rangePickerMinDays,o=t.rangePickerMaxDays;if(a){var i;this.value||(this.value=[]);for(var s=0;s<this.value.length;s+=1)new Date(e).getTime()===new Date(this.value[s]).getTime()&&(i=s);void 0===i?this.value.push(e):this.value.splice(i,1),this.updateValue()}else n?(this.value||(this.value=[]),2!==this.value.length&&0!==this.value.length||(this.value=[]),0===this.value.length||Math.abs(this.value[0].getTime()-e.getTime())>=60*(r-1)*60*24*1e3&&(0===o||Math.abs(this.value[0].getTime()-e.getTime())<=60*(o-1)*60*24*1e3)?this.value.push(e):this.value=[],this.value.sort((function(e,t){return e-t})),this.updateValue()):(this.value=[e],this.updateValue())},t.prototype.setValue=function(e){var t=this.value;if(Array.isArray(t)&&Array.isArray(e)&&t.length===e.length){var a=!0;if(t.forEach((function(t,n){t!==e[n]&&(a=!1)})),a)return}this.value=e,this.updateValue()},t.prototype.getValue=function(){return this.value},t.prototype.updateValue=function(e){var t,a,n=this.$el,r=this.$wrapperEl,o=this.$inputEl,i=this.value,s=this.params;if(n&&n.length>0)if(r.find(".calendar-day-selected").removeClass("calendar-day-selected"),s.rangePicker&&2===i.length)for(t=new Date(i[0]).getTime();t<=new Date(i[1]).getTime();t+=864e5)a=new Date(t),r.find('.calendar-day[data-date="'+a.getFullYear()+"-"+a.getMonth()+"-"+a.getDate()+'"]').addClass("calendar-day-selected");else for(t=0;t<this.value.length;t+=1)a=new Date(i[t]),r.find('.calendar-day[data-date="'+a.getFullYear()+"-"+a.getMonth()+"-"+a.getDate()+'"]').addClass("calendar-day-selected");if(e||this.emit("local::change calendarChange",this,i),n&&n.length>0&&this.hasTimePicker&&n.find(".calendar-time-selector a").text(i&&i.length?this.timeSelectorFormatter.format(i[0]):this.params.timePickerPlaceholder),o&&o.length||s.header){var l=this.formatValue(i);s.header&&n&&n.length&&n.find(".calendar-selected-date").text(l),o&&o.length&&!e&&(o.val(l),o.trigger("change"))}},t.prototype.updateCurrentMonthYear=function(e){var t,a,n=this.$months,r=this.$el,o=this.monthNames;void 0===e?(this.currentMonth=parseInt(n.eq(1).attr("data-month"),10),this.currentYear=parseInt(n.eq(1).attr("data-year"),10),t=n.eq(1).attr("data-locale-month"),a=n.eq(1).attr("data-locale-year")):(this.currentMonth=parseInt(n.eq("next"===e?n.length-1:0).attr("data-month"),10),this.currentYear=parseInt(n.eq("next"===e?n.length-1:0).attr("data-year"),10),t=n.eq("next"===e?n.length-1:0).attr("data-locale-month"),a=n.eq("next"===e?n.length-1:0).attr("data-locale-year")),r.find(".current-month-value").text(o[t]),r.find(".current-year-value").text(a)},t.prototype.update=function(){var e=this,t=e.currentYear,a=e.currentMonth,n=e.$wrapperEl,r=new Date(t,a),o=e.renderMonth(r,"prev"),i=e.renderMonth(r),s=e.renderMonth(r,"next");n.transition(0).html(""+o+i+s).transform("translate3d(0,0,0)"),e.$months=n.find(".calendar-month"),e.monthsTranslate=0,e.setMonthsTranslate(),e.$months.each((function(t,a){e.emit("local::monthAdd calendarMonthAdd",a)}))},t.prototype.onMonthChangeStart=function(e){var t=this.$months,a=this.currentYear,n=this.currentMonth;this.updateCurrentMonthYear(e),t.removeClass("calendar-month-current calendar-month-prev calendar-month-next");var r="next"===e?t.length-1:0;t.eq(r).addClass("calendar-month-current"),t.eq("next"===e?r-1:r+1).addClass("next"===e?"calendar-month-prev":"calendar-month-next"),this.emit("local::monthYearChangeStart calendarMonthYearChangeStart",this,a,n)},t.prototype.onMonthChangeEnd=function(e,t){var a,n,r,o=this.currentYear,i=this.currentMonth,s=this.$wrapperEl,l=this.monthsTranslate;this.animating=!1,s.find(".calendar-month:not(.calendar-month-prev):not(.calendar-month-current):not(.calendar-month-next)").remove(),void 0===e&&(e="next",t=!0),t?(s.find(".calendar-month-next, .calendar-month-prev").remove(),n=this.renderMonth(new Date(o,i),"prev"),a=this.renderMonth(new Date(o,i),"next")):r=this.renderMonth(new Date(o,i),e),("next"===e||t)&&s.append(r||a),("prev"===e||t)&&s.prepend(r||n);var c=s.find(".calendar-month");this.$months=c,this.setMonthsTranslate(l),this.emit("local::monthAdd calendarMonthAdd",this,"next"===e?c.eq(c.length-1)[0]:c.eq(0)[0]),this.emit("local::monthYearChangeEnd calendarMonthYearChangeEnd",this,o,i)},t.prototype.setMonthsTranslate=function(e){var t=this.$months,a=this.isHorizontal,n=this.inverter;e=e||this.monthsTranslate||0,void 0===this.monthsTranslate&&(this.monthsTranslate=e),t.removeClass("calendar-month-current calendar-month-prev calendar-month-next");var r=100*-(e+1)*n,o=100*-e*n,i=100*-(e-1)*n;t.eq(0).transform("translate3d("+(a?r:0)+"%, "+(a?0:r)+"%, 0)").addClass("calendar-month-prev"),t.eq(1).transform("translate3d("+(a?o:0)+"%, "+(a?0:o)+"%, 0)").addClass("calendar-month-current"),t.eq(2).transform("translate3d("+(a?i:0)+"%, "+(a?0:i)+"%, 0)").addClass("calendar-month-next")},t.prototype.nextMonth=function(e){var t=this,n=t.params,r=t.$wrapperEl,o=t.inverter,i=t.isHorizontal;void 0!==e&&"object"!=typeof e||(e="",n.animate||(e=0));var s=parseInt(t.$months.eq(t.$months.length-1).attr("data-month"),10),l=parseInt(t.$months.eq(t.$months.length-1).attr("data-year"),10),c=new Date(l,s).getTime(),d=!t.animating;if(n.maxDate&&c>new Date(n.maxDate).getTime())t.resetMonth();else{if(t.monthsTranslate-=1,s===t.currentMonth){var h=100*-t.monthsTranslate*o,m=a(t.renderMonth(c,"next")).transform("translate3d("+(i?h:0)+"%, "+(i?0:h)+"%, 0)").addClass("calendar-month-next");r.append(m[0]),t.$months=r.find(".calendar-month"),t.emit("local::monthAdd calendarMonthAdd",t.$months.eq(t.$months.length-1)[0])}t.animating=!0,t.onMonthChangeStart("next");var p=100*t.monthsTranslate*o;r.transition(e).transform("translate3d("+(i?p:0)+"%, "+(i?0:p)+"%, 0)"),d&&r.transitionEnd((function(){t.onMonthChangeEnd("next")})),n.animate||t.onMonthChangeEnd("next")}},t.prototype.prevMonth=function(e){var t=this,n=t.params,r=t.$wrapperEl,o=t.inverter,i=t.isHorizontal;void 0!==e&&"object"!=typeof e||(e="",n.animate||(e=0));var s=parseInt(t.$months.eq(0).attr("data-month"),10),l=parseInt(t.$months.eq(0).attr("data-year"),10),c=new Date(l,s+1,-1).getTime(),d=!t.animating;if(n.minDate){var h=new Date(n.minDate);if(c<(h=new Date(h.getFullYear(),h.getMonth(),1)).getTime())return void t.resetMonth()}if(t.monthsTranslate+=1,s===t.currentMonth){var m=100*-t.monthsTranslate*o,p=a(t.renderMonth(c,"prev")).transform("translate3d("+(i?m:0)+"%, "+(i?0:m)+"%, 0)").addClass("calendar-month-prev");r.prepend(p[0]),t.$months=r.find(".calendar-month"),t.emit("local::monthAdd calendarMonthAdd",t.$months.eq(0)[0])}t.animating=!0,t.onMonthChangeStart("prev");var u=100*t.monthsTranslate*o;r.transition(e).transform("translate3d("+(i?u:0)+"%, "+(i?0:u)+"%, 0)"),d&&r.transitionEnd((function(){t.onMonthChangeEnd("prev")})),n.animate||t.onMonthChangeEnd("prev")},t.prototype.resetMonth=function(e){void 0===e&&(e="");var t=this.$wrapperEl,a=this.inverter,n=this.isHorizontal,r=100*this.monthsTranslate*a;t.transition(e).transform("translate3d("+(n?r:0)+"%, "+(n?0:r)+"%, 0)")},t.prototype.setYearMonth=function(e,t,a){var n,r=this,o=r.params,i=r.isHorizontal,s=r.$wrapperEl,l=r.inverter;if(void 0===e&&(e=r.currentYear),void 0===t&&(t=r.currentMonth),void 0!==a&&"object"!=typeof a||(a="",o.animate||(a=0)),n=e<r.currentYear?new Date(e,t+1,-1).getTime():new Date(e,t).getTime(),o.maxDate&&n>new Date(o.maxDate).getTime())return!1;if(o.minDate){var c=new Date(o.minDate);if(n<(c=new Date(c.getFullYear(),c.getMonth(),1)).getTime())return!1}var d=new Date(r.currentYear,r.currentMonth).getTime(),h=n>d?"next":"prev",m=r.renderMonth(new Date(e,t));r.monthsTranslate=r.monthsTranslate||0;var p,u=r.monthsTranslate,v=!r.animating&&0!==a;n>d?(r.monthsTranslate-=1,r.animating||r.$months.eq(r.$months.length-1).remove(),s.append(m),r.$months=s.find(".calendar-month"),p=100*-(u-1)*l,r.$months.eq(r.$months.length-1).transform("translate3d("+(i?p:0)+"%, "+(i?0:p)+"%, 0)").addClass("calendar-month-next")):(r.monthsTranslate+=1,r.animating||r.$months.eq(0).remove(),s.prepend(m),r.$months=s.find(".calendar-month"),p=100*-(u+1)*l,r.$months.eq(0).transform("translate3d("+(i?p:0)+"%, "+(i?0:p)+"%, 0)").addClass("calendar-month-prev")),r.emit("local::monthAdd calendarMonthAdd","next"===h?r.$months.eq(r.$months.length-1)[0]:r.$months.eq(0)[0]),r.animating=!0,r.onMonthChangeStart(h);var f=100*r.monthsTranslate*l;s.transition(a).transform("translate3d("+(i?f:0)+"%, "+(i?0:f)+"%, 0)"),v&&s.transitionEnd((function(){r.onMonthChangeEnd(h,!0)})),o.animate&&0!==a||r.onMonthChangeEnd(h,!0)},t.prototype.nextYear=function(){this.setYearMonth(this.currentYear+1)},t.prototype.prevYear=function(){this.setYearMonth(this.currentYear-1)},t.prototype.dateInRange=function(e,t){var a,n=!1;if(!t)return!1;if(Array.isArray(t))for(a=0;a<t.length;a+=1)t[a].from||t[a].to?t[a].from&&t[a].to?e<=new Date(t[a].to).getTime()&&e>=new Date(t[a].from).getTime()&&(n=!0):t[a].from?e>=new Date(t[a].from).getTime()&&(n=!0):t[a].to&&e<=new Date(t[a].to).getTime()&&(n=!0):t[a].date?e===new Date(t[a].date).getTime()&&(n=!0):e===new Date(t[a]).getTime()&&(n=!0);else t.from||t.to?t.from&&t.to?e<=new Date(t.to).getTime()&&e>=new Date(t.from).getTime()&&(n=!0):t.from?e>=new Date(t.from).getTime()&&(n=!0):t.to&&e<=new Date(t.to).getTime()&&(n=!0):t.date?n=e===new Date(t.date).getTime():"function"==typeof t&&(n=t(new Date(e)));return n},t.prototype.daysInMonth=function(e){var t=new Date(e);return new Date(t.getFullYear(),t.getMonth()+1,0).getDate()},t.prototype.renderMonths=function(e){return this.params.renderMonths?this.params.renderMonths.call(this,e):('\n    <div class="calendar-months-wrapper">\n    '+this.renderMonth(e,"prev")+"\n    "+this.renderMonth(e)+"\n    "+this.renderMonth(e,"next")+"\n    </div>\n  ").trim()},t.prototype.renderMonth=function(e,t){var a=this,n=a.params,r=a.value;if(n.renderMonth)return n.renderMonth.call(a,e,t);var o=new Date(e),i=o.getFullYear(),s=o.getMonth(),l=a.monthNames.indexOf(a.monthFormatter.format(o));l<0&&(l=s);var c=a.yearFormatter.format(o);"next"===t&&(o=11===s?new Date(i+1,0):new Date(i,s+1,1)),"prev"===t&&(o=0===s?new Date(i-1,11):new Date(i,s-1,1)),"next"!==t&&"prev"!==t||(s=o.getMonth(),i=o.getFullYear(),(l=a.monthNames.indexOf(a.monthFormatter.format(o)))<0&&(l=s),c=a.yearFormatter.format(o));var d,h,m=[],p=(new Date).setHours(0,0,0,0),u=n.minDate?new Date(n.minDate).getTime():null,v=n.maxDate?new Date(n.maxDate).getTime():null,f=a.daysInMonth(new Date(o.getFullYear(),o.getMonth()).getTime()-864e6),g=a.daysInMonth(o),y=6===n.firstDay?0:1,k="",M=n.firstDay-1+0,D=new Date(o.getFullYear(),o.getMonth()).getDay();if(0===D&&(D=7),r&&r.length)for(var w=0;w<r.length;w+=1)m.push(new Date(r[w]).setHours(0,0,0,0));for(var T=1;T<=6;T+=1){for(var $="",C=function(e){var t=void 0,r=(M+=1)-D,o="";1===T&&1===e&&r>y&&1!==n.firstDay&&(r=(M-=7)-D);var l=e-1+n.firstDay>6?e-1-7+n.firstDay:e-1+n.firstDay;r<0?(r=f+r+1,o+=" calendar-day-prev",t=new Date(s-1<0?i-1:i,s-1<0?11:s-1,r).getTime()):(r+=1)>g?(r-=g,o+=" calendar-day-next",t=new Date(s+1>11?i+1:i,s+1>11?0:s+1,r).getTime()):t=new Date(i,s,r).getTime(),t===p&&(o+=" calendar-day-today"),n.rangePicker&&2===m.length?t>=m[0]&&t<=m[1]&&(o+=" calendar-day-selected"):m.indexOf(t)>=0&&(o+=" calendar-day-selected"),n.weekendDays.indexOf(l)>=0&&(o+=" calendar-day-weekend");var c="";if(h=!1,n.events&&a.dateInRange(t,n.events)&&(h=!0),h&&(o+=" calendar-day-has-events",c='\n            <span class="calendar-day-events">\n              <span class="calendar-day-event"></span>\n            </span>\n          ',Array.isArray(n.events))){var k=[];n.events.forEach((function(e){var n=e.color||"";k.indexOf(n)<0&&a.dateInRange(t,e)&&k.push(n)})),c='\n              <span class="calendar-day-events">\n                '+k.map((function(e){return('\n                  <span class="calendar-day-event" style="'+(e?"background-color: "+e:"")+'"></span>\n                ').trim()})).join("")+"\n              </span>\n            "}if(n.rangesClasses)for(var w=0;w<n.rangesClasses.length;w+=1)a.dateInRange(t,n.rangesClasses[w].range)&&(o+=" "+n.rangesClasses[w].cssClass);d=!1,(u&&t<u||v&&t>v)&&(d=!0),n.disabled&&a.dateInRange(t,n.disabled)&&(d=!0),d&&(o+=" calendar-day-disabled");var C=(t=new Date(t)).getFullYear(),P=t.getMonth(),x=a.dayFormatter.format(t);$+=('\n          <div data-year="'+C+'" data-month="'+P+'" data-day="'+r+'" class="calendar-day'+o+'" data-date="'+C+"-"+P+"-"+r+'">\n            <span class="calendar-day-number">'+x+c+"</span>\n          </div>").trim()},P=1;P<=7;P+=1)C(P);k+='<div class="calendar-row">'+$+"</div>"}return k='<div class="calendar-month" data-year="'+i+'" data-month="'+s+'" data-locale-year="'+c+'" data-locale-month="'+l+'">'+k+"</div>"},t.prototype.renderWeekHeader=function(){if(this.params.renderWeekHeader)return this.params.renderWeekHeader.call(this);for(var e=this.params,t="",a=0;a<7;a+=1){var n=a+e.firstDay>6?a-7+e.firstDay:a+e.firstDay;t+='<div class="calendar-week-day">'+this.dayNamesShort[n]+"</div>"}return('\n    <div class="calendar-week-header">\n      '+t+"\n    </div>\n  ").trim()},t.prototype.renderMonthSelector=function(){return this.params.renderMonthSelector?this.params.renderMonthSelector.call(this):('\n    <div class="calendar-month-selector">\n      <a class="link icon-only calendar-prev-month-button">\n        <i class="icon icon-prev"></i>\n      </a>\n      '+(this.params.monthPicker?'\n        <a class="current-month-value link"></a>\n      ':'\n        <span class="current-month-value"></span>\n      ')+'\n      <a class="link icon-only calendar-next-month-button">\n        <i class="icon icon-next"></i>\n      </a>\n    </div>\n  ').trim()},t.prototype.renderMonthPicker=function(){var e=parseInt(this.$el.find(".calendar-month-current").attr("data-locale-month"),10);return'\n      <div class="calendar-month-picker">\n        '+this.monthNames.map((function(t,a){return'\n          <div class="calendar-month-picker-item '+(e===a?"calendar-month-picker-item-current":"")+'">\n            <span>'+t+"</span>\n          </div>\n        "})).join("")+"\n      </div>\n    "},t.prototype.renderYearSelector=function(){return this.params.renderYearSelector?this.params.renderYearSelector.call(this):('\n    <div class="calendar-year-selector">\n      <a class="link icon-only calendar-prev-year-button">\n        <i class="icon icon-prev"></i>\n      </a>\n      '+(this.params.yearPicker?'\n        <a class="current-year-value link"></a>\n      ':'\n        <span class="current-year-value"></span>\n      ')+'\n      <a class="link icon-only calendar-next-year-button">\n        <i class="icon icon-next"></i>\n      </a>\n    </div>\n  ').trim()},t.prototype.renderYearPicker=function(){var e=this,t=e.currentYear,a=e.params.yearPickerMin||(new Date).getFullYear()-100;e.params.minDate&&(a=Math.max(a,new Date(e.params.minDate).getFullYear()));var n=e.params.yearPickerMax||(new Date).getFullYear()+100;e.params.maxDate&&(n=Math.min(n,new Date(e.params.maxDate).getFullYear()));for(var r=[],o=a;o<=n;o+=1)r.push(o);return'\n      <div class="calendar-year-picker">\n        '+r.map((function(a){return'\n          <div data-year="'+a+'" class="calendar-year-picker-item '+(a===t?"calendar-year-picker-item-current":"")+'">\n            <span>'+e.yearFormatter.format((new Date).setFullYear(a))+"</span>\n          </div>\n        "})).join("")+"\n      </div>\n    "},t.prototype.renderTimeSelector=function(){var e,t=this.value&&this.value[0];return t&&(e=this.timeSelectorFormatter.format(t)),'\n      <div class="calendar-time-selector"><a class="link">'+(e||this.params.timePickerPlaceholder)+"</a></div>\n    "},t.prototype.renderHeader=function(){return this.params.renderHeader?this.params.renderHeader.call(this):('\n    <div class="calendar-header">\n      <div class="calendar-selected-date">'+this.params.headerPlaceholder+"</div>\n    </div>\n  ").trim()},t.prototype.renderFooter=function(){var e=this.app;return this.params.renderFooter?this.params.renderFooter.call(this):('\n    <div class="calendar-footer">\n      <a class="'+("md"===e.theme?"button":"link")+' calendar-close sheet-close popover-close">'+this.params.toolbarCloseText+"</a>\n    </div>\n  ").trim()},t.prototype.renderToolbar=function(){return this.params.renderToolbar?this.params.renderToolbar.call(this,this):('\n    <div class="toolbar toolbar-top no-shadow">\n      <div class="toolbar-inner">\n        '+(this.params.monthSelector?this.renderMonthSelector():"")+"\n        "+(this.params.yearSelector?this.renderYearSelector():"")+"\n      </div>\n    </div>\n  ").trim()},t.prototype.renderInline=function(){var e=this.params,t=e.cssClass,a=e.toolbar,n=e.header,r=e.footer,o=e.rangePicker,i=e.weekHeader,s=this.value,l=this.hasTimePicker,c=s&&s.length?s[0]:(new Date).setHours(0,0,0);return('\n    <div class="calendar calendar-inline '+(o?"calendar-range":"")+" "+(t||"")+'">\n      '+(n?this.renderHeader():"")+"\n      "+(a?this.renderToolbar():"")+"\n      "+(i?this.renderWeekHeader():"")+'\n      <div class="calendar-months">\n        '+this.renderMonths(c)+"\n      </div>\n      "+(l?this.renderTimeSelector():"")+"\n      "+(r?this.renderFooter():"")+"\n    </div>\n  ").trim()},t.prototype.renderCustomModal=function(){var e=this.params,t=e.cssClass,a=e.toolbar,n=e.header,r=e.footer,o=e.rangePicker,i=e.weekHeader,s=this.value,l=this.hasTimePicker,c=s&&s.length?s[0]:(new Date).setHours(0,0,0);return('\n    <div class="calendar calendar-modal '+(o?"calendar-range":"")+" "+(t||"")+'">\n      '+(n?this.renderHeader():"")+"\n      "+(a?this.renderToolbar():"")+"\n      "+(i?this.renderWeekHeader():"")+'\n      <div class="calendar-months">\n        '+this.renderMonths(c)+"\n      </div>\n      "+(l?this.renderTimeSelector():"")+"\n      "+(r?this.renderFooter():"")+"\n    </div>\n  ").trim()},t.prototype.renderSheet=function(){var e=this.params,t=e.cssClass,a=e.toolbar,n=e.header,r=e.footer,o=e.rangePicker,i=e.weekHeader,s=this.value,l=this.hasTimePicker,c=s&&s.length?s[0]:(new Date).setHours(0,0,0);return('\n    <div class="sheet-modal calendar calendar-sheet '+(o?"calendar-range":"")+" "+(t||"")+'">\n      '+(n?this.renderHeader():"")+"\n      "+(a?this.renderToolbar():"")+"\n      "+(i?this.renderWeekHeader():"")+'\n      <div class="sheet-modal-inner calendar-months">\n        '+this.renderMonths(c)+"\n      </div>\n      "+(l?this.renderTimeSelector():"")+"\n      "+(r?this.renderFooter():"")+"\n    </div>\n  ").trim()},t.prototype.renderPopover=function(){var e=this.params,t=e.cssClass,a=e.toolbar,n=e.header,r=e.footer,o=e.rangePicker,i=e.weekHeader,s=this.value,l=this.hasTimePicker,c=s&&s.length?s[0]:(new Date).setHours(0,0,0);return('\n    <div class="popover calendar-popover">\n      <div class="popover-inner">\n        <div class="calendar '+(o?"calendar-range":"")+" "+(t||"")+'">\n        '+(n?this.renderHeader():"")+"\n        "+(a?this.renderToolbar():"")+"\n        "+(i?this.renderWeekHeader():"")+'\n        <div class="calendar-months">\n          '+this.renderMonths(c)+"\n        </div>\n        "+(l?this.renderTimeSelector():"")+"\n        "+(r?this.renderFooter():"")+"\n        </div>\n      </div>\n    </div>\n  ").trim()},t.prototype.render=function(){var e=this.params;if(e.render)return e.render.call(this);if(!this.inline){var t=e.openIn;return"auto"===t&&(t=this.isPopover()?"popover":"sheet"),"popover"===t?this.renderPopover():"sheet"===t?this.renderSheet():this.renderCustomModal()}return this.renderInline()},t.prototype.openTimePicker=function(){var e=this.$el,t=this.app;if(e&&e.length){e.append('<div class="calendar-time-picker"></div>');for(var a,n=[],r=[],o=0;o<=23;o+=1)n.push(o);for(var i=0;i<=59;i+=1)r.push(i);a=this.value&&this.value.length?[this.value[0].getHours(),this.value[0].getMinutes()]:[(new Date).getHours(),(new Date).getMinutes()],this.timePickerInstance=t.picker.create({containerEl:e.find(".calendar-time-picker"),value:a,toolbar:!0,rotateEffect:!1,toolbarCloseText:this.params.toolbarCloseText,cols:[{values:n},{divider:!0,content:":"},{values:r,displayValues:r.map((function(e){return e<10?"0"+e:e}))}]}),this.timePickerInstance.$el.find(".toolbar a").removeClass("sheet-close popover-close").addClass("calendar-time-picker-close")}},t.prototype.closeTimePicker=function(){if(this.timePickerInstance){var e=this.timePickerInstance.value.map((function(e){return parseInt(e,10)})),t=e[0],a=e[1],n=this.value&&this.value.length&&this.value[0];n?(n=new Date(n)).setHours(t,a):(n=new Date).setHours(t,a,0,0),this.setValue([n]),this.timePickerInstance.close(),this.timePickerInstance.destroy(),delete this.timePickerInstance}this.$el&&this.$el.length&&this.$el.find(".calendar-time-picker").remove()},t.prototype.onOpen=function(){var e=this,t=e.initialized,a=e.$el,n=e.app,r=e.$inputEl,o=e.inline,i=e.value,s=e.params;e.closing=!1,e.opened=!0,e.opening=!0,e.attachCalendarEvents();var l=!i&&s.value;t?i&&e.setValue(i,0):i?e.setValue(i,0):s.value&&e.setValue(e.normalizeValues(s.value),0),e.updateCurrentMonthYear(),e.monthsTranslate=0,e.setMonthsTranslate(),l?e.updateValue():s.header&&i&&e.updateValue(!0),!o&&r&&r.length&&"md"===n.theme&&r.trigger("focus"),e.initialized=!0,e.$months.each((function(t,a){e.emit("local::monthAdd calendarMonthAdd",a)})),a&&a.trigger("calendar:open"),r&&r.trigger("calendar:open"),e.emit("local::open calendarOpen",e)},t.prototype.onOpened=function(){this.opening=!1,this.$el&&this.$el.trigger("calendar:opened"),this.$inputEl&&this.$inputEl.trigger("calendar:opened"),this.emit("local::opened calendarOpened",this)},t.prototype.onClose=function(){var e=this.app;this.opening=!1,this.closing=!0,this.$inputEl&&"md"===e.theme&&this.$inputEl.trigger("blur"),this.detachCalendarEvents&&this.detachCalendarEvents(),this.$el&&this.$el.trigger("calendar:close"),this.$inputEl&&this.$inputEl.trigger("calendar:close"),this.emit("local::close calendarClose",this)},t.prototype.onClosed=function(){var e=this;e.opened=!1,e.closing=!1,e.inline||n.nextTick((function(){e.modal&&e.modal.el&&e.modal.destroy&&(e.params.routableModals||e.modal.destroy()),delete e.modal})),e.timePickerInstance&&(e.timePickerInstance.destroy&&e.timePickerInstance.destroy(),delete e.timePickerInstance),e.$el&&e.$el.trigger("calendar:closed"),e.$inputEl&&e.$inputEl.trigger("calendar:closed"),e.emit("local::closed calendarClosed",e)},t.prototype.open=function(){var e,t=this,n=t.app,r=t.opened,o=t.inline,i=t.$inputEl,s=t.params;if(!r){if(o)return t.$el=a(t.render()),t.$el[0].f7Calendar=t,t.$wrapperEl=t.$el.find(".calendar-months-wrapper"),t.$months=t.$wrapperEl.find(".calendar-month"),t.$containerEl.append(t.$el),t.onOpen(),void t.onOpened();var l=s.openIn;"auto"===l&&(l=t.isPopover()?"popover":"sheet");var c=t.render(),d={targetEl:i,scrollToEl:s.scrollToInput?i:void 0,content:c,backdrop:!0===s.backdrop||"popover"===l&&!1!==n.params.popover.backdrop&&!1!==s.backdrop,closeByBackdropClick:s.closeByBackdropClick,on:{open:function(){t.modal=this,t.$el="popover"===l?this.$el.find(".calendar"):this.$el,t.$wrapperEl=t.$el.find(".calendar-months-wrapper"),t.$months=t.$wrapperEl.find(".calendar-month"),t.$el[0].f7Calendar=t,"customModal"===l&&a(t.$el).find(".calendar-close").once("click",(function(){t.close()})),t.onOpen()},opened:function(){t.onOpened()},close:function(){t.onClose()},closed:function(){t.onClosed()}}};"sheet"===l&&(d.push=s.sheetPush,d.swipeToClose=s.sheetSwipeToClose),s.routableModals&&t.view?t.view.router.navigate({url:t.url,route:(e={path:t.url},e[l]=d,e)}):(t.modal=n[l].create(d),t.modal.open())}},t.prototype.close=function(){var e=this.opened,t=this.inline;if(e)return t?(this.onClose(),void this.onClosed()):void(this.params.routableModals&&this.view?this.view.router.back():this.modal.close())},t.prototype.init=function(){if(this.initInput(),this.inline)return this.open(),void this.emit("local::init calendarInit",this);!this.initialized&&this.params.value&&this.setValue(this.normalizeValues(this.params.value)),this.$inputEl&&this.attachInputEvents(),this.params.closeByOutsideClick&&this.attachHtmlEvents(),this.emit("local::init calendarInit",this)},t.prototype.destroy=function(){if(!this.destroyed){var e=this.$el;this.emit("local::beforeDestroy calendarBeforeDestroy",this),e&&e.trigger("calendar:beforedestroy"),this.close(),this.$inputEl&&this.detachInputEvents(),this.params.closeByOutsideClick&&this.detachHtmlEvents(),this.timePickerInstance&&(this.timePickerInstance.destroy&&this.timePickerInstance.destroy(),delete this.timePickerInstance),e&&e.length&&delete this.$el[0].f7Calendar,n.deleteProps(this),this.destroyed=!0}},Object.defineProperties(t.prototype,r),t}(r)),s={name:"calendar",static:{Calendar:i},create:function(){this.calendar=o({defaultSelector:".calendar",constructor:i,app:this,domProp:"f7Calendar"}),this.calendar.close=function(e){void 0===e&&(e=".calendar");var t=a(e);if(0!==t.length){var n=t[0].f7Calendar;!n||n&&!n.opened||n.close()}}},params:{calendar:{dateFormat:void 0,monthNames:"auto",monthNamesShort:"auto",dayNames:"auto",dayNamesShort:"auto",locale:void 0,firstDay:1,weekendDays:[0,6],multiple:!1,rangePicker:!1,rangePickerMinDays:1,rangePickerMaxDays:0,direction:"horizontal",minDate:null,maxDate:null,disabled:null,events:null,rangesClasses:null,touchMove:!0,animate:!0,closeOnSelect:!1,monthSelector:!0,monthPicker:!0,yearSelector:!0,yearPicker:!0,yearPickerMin:void 0,yearPickerMax:void 0,timePicker:!1,timePickerFormat:{hour:"numeric",minute:"numeric"},timePickerPlaceholder:"Select time",weekHeader:!0,value:null,containerEl:null,openIn:"auto",sheetPush:!1,sheetSwipeToClose:void 0,formatValue:null,inputEl:null,inputReadOnly:!0,closeByOutsideClick:!0,scrollToInput:!0,header:!1,headerPlaceholder:"Select date",toolbar:!0,toolbarCloseText:"Done",footer:!1,cssClass:null,routableModals:!0,view:null,url:"date/",backdrop:null,closeByBackdropClick:!0,renderWeekHeader:null,renderMonths:null,renderMonth:null,renderMonthSelector:null,renderYearSelector:null,renderHeader:null,renderFooter:null,renderToolbar:null,renderInline:null,renderPopover:null,renderSheet:null,render:null}}};if(t){if(e.prototype.modules&&e.prototype.modules[s.name])return;e.use(s),e.instance&&(e.instance.useModuleParams(s,e.instance.params),e.instance.useModule(s))}return s}(Framework7, typeof Framework7AutoInstallComponent === 'undefined' ? undefined : Framework7AutoInstallComponent))