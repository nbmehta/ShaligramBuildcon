/*
 * Tracelytics PageGuide
 *
 * Copyright 2012 Tracelytics
 * Free to use under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
 *
 * Contributing Author: Tracelytics Team
 */

/*
 * PageGuide usage:
 *
 *   Preferences:
 *     auto_show_first - Whether or not to focus on the first visible item
 *                       immediately on PG open (default true)
 *     loading_selector - The CSS selector for the loading element. pageguide
 *                        will wait until this element is no longer visible
 *                        starting up.
 *     track_events_cb - Optional callback for tracking user interactions
 *                       with pageguide.  Should be a method taking a single
 *                       parameter indicating the name of the interaction.
 *                       (default none)
 */
 
/* If you change the height of the tlyPageGuideMessages div then you need to alter the code that closes the page guide when you click on the navigation tabs  - ALM */
 
tl = window.tl || {};
tl.pg = tl.pg || {};

tl.pg.default_prefs = {
    'auto_show_first': true,
    'loading_selector' : '#loading',
    'track_events_cb': function() { return; }
};

tl.pg.init = function(preferences) {
    /* page guide object, for pages that have one */
    if (jQuery("#tlyPageGuide").length === 0) {
        return;
    }

    var guide   = jQuery("#tlyPageGuide"),
        wrapper = jQuery('<div>', { id: 'tlyPageGuideWrapper' }),
        message = jQuery('<div>', { id: 'tlyPageGuideMessages'});

    message.append('<a href="#" class="tlypageguide_close" title="Close Guide">close</a>')
      .append('<span></span>')
      .append('<div></div>')
      .append('<a href="#" class="tlypageguide_back" title="Next">Previous</a>')
      .append('<a href="#" class="tlypageguide_fwd" title="Next">Next</a>');

    jQuery('<div/>', {
        'title': 'Launch Page Guide',
        'class': 'tlypageguide_toggle'
    }).append('')
      .append('<div><span>' + guide.data('tourtitle') + '</span></div>')
      .append('<a href="javascript:void(0);" title="close guide">close guide �</a>').appendTo(wrapper);

    wrapper.append(guide);
    wrapper.append(message);
    jQuery('body').append(wrapper);

    var pg = new tl.pg.PageGuide(jQuery('#tlyPageGuideWrapper'), preferences);
    pg.ready(function() {
        pg.setup_handlers();
        pg.$base.children(".tlypageguide_toggle").animate({ "bottom": "-3px" }, 1000);
    });
    return pg;
};

tl.pg.PageGuide = function (pg_elem, preferences) {
    this.preferences = jQuery.extend({}, tl.pg.default_prefs, preferences);
    this.$base = pg_elem;
    this.$all_items = jQuery('#tlyPageGuide > li', this.$base);
    this.$items = jQuery([]); /* fill me with visible elements on pg expand */
    this.$message = jQuery('#tlyPageGuideMessages');
    this.$fwd = jQuery('a.tlypageguide_fwd', this.$base);
    this.$back = jQuery('a.tlypageguide_back', this.$base);
    this.cur_idx = 0;
    this.track_event = this.preferences.track_events_cb;
};

tl.pg.isScrolledIntoView = function(elem) {
    var dvtop = jQuery(window).scrollTop(),
        dvbtm = dvtop + jQuery(window).height(),
        eltop = jQuery(elem).offset().top,
        elbtm = eltop + jQuery(elem).height();

    return (elbtm >= dvtop) && (eltop <= dvbtm - 100);
};

tl.pg.PageGuide.prototype.ready = function(callback) {
    var that = this,
        interval = window.setInterval(function() {
            if (!jQuery(that.preferences.loading_selector).is(':visible')) {
                callback();
                clearInterval(interval);
            }
        }, 250);
    return this;
};

/* to be executed on pg expand */
tl.pg.PageGuide.prototype._on_expand = function () {
    var that = this,
        $d = document,
        $w = window;

    /* set up initial state */
    this.position_tour();
    this.cur_idx = 0;

    // create a new stylesheet:
    var ns = $d.createElement('style');
    $d.getElementsByTagName('head')[0].appendChild(ns);

    // keep Safari happy
    if (!$w.createPopup) {
        ns.appendChild($d.createTextNode(''));
        ns.setAttribute("type", "text/css");
		ns.setAttribute("class", "pageGuideStyle");
    }

    // get a pointer to the stylesheet you just created
    var sh = $d.styleSheets[$d.styleSheets.length - 1];

    // space for IE rule set
    var ie = "";

    /* add number tags and PG shading elements */
    this.$items.each(function(i) {
        var $p = jQuery(jQuery(this).data('tourtarget') + ":visible:first");
        $p.addClass("tlypageguide_shadow tlypageguide_shadow" + i);

        var node_text = '.tlypageguide_shadow' + i + ':after { height: ' +
                            $p.outerHeight() + 'px; width: ' + $p.outerWidth(false) + 'px; }';

        if (!$w.createPopup) {
            // modern browsers
            var k = $d.createTextNode(node_text, 0);
            ns.appendChild(k);
        } else {
            // for IE
            ie += node_text;
        }

        jQuery(this).prepend('<ins>' + (i + 1) + '</ins>');
        jQuery(this).data('idx', i);
    });

    // is IE? slam styles in all at once:
    if ($w.createPopup) {
        sh.cssText = ie;
    }

    /* decide to show first? */
    if (this.preferences.auto_show_first && this.$items.length > 0) {
        this.show_message(0);
    }
};

tl.pg.PageGuide.prototype.open = function() {
    this.track_event('PG.open');

    this._on_expand();
    this.$items.toggleClass('expanded');
    jQuery('body').addClass('tlypageguide-open');
};

tl.pg.PageGuide.prototype.close = function() {
    this.track_event('PG.close');

    this.$items.toggleClass('expanded');
    this.$message.animate({ height: "0" }, 500, function() {
        jQuery(this).hide();
    });
    /* clear number tags and shading elements */
    jQuery('ins').remove();
    jQuery('body').removeClass('tlypageguide-open');
};

tl.pg.PageGuide.prototype.setup_handlers = function () {
    var that = this;

    /* interaction: open/close PG interface */
    jQuery('.tlypageguide_toggle', this.$base).bind('click', function() {
        if (jQuery('body').is('.tlypageguide-open')) {
            that.close();
        } else {
            that.open();
        }
        return false;
    });

    jQuery('.tlypageguide_close', this.$message).bind('click', function() {
        that.close();
        return false;
    });

    /* interaction: item click */
    this.$all_items.bind('click', function() {
        var new_index = jQuery(this).data('idx');

        that.track_event('PG.specific_elt');
        that.show_message(new_index);
    });

    /* interaction: fwd/back click */
    this.$fwd.bind('click', function() {
        var new_index = (that.cur_idx + 1) % that.$items.length;

        that.track_event('PG.fwd');
        that.show_message(new_index);
        return false;
    });

    this.$back.bind('click', function() {
        /*
         * If -n < x < 0, then the result of x % n will be x, which is
         * negative. To get a positive remainder, compute (x + n) % n.
         */
        var new_index = (that.cur_idx + that.$items.length - 1) % that.$items.length;

        that.track_event('PG.back');
        that.show_message(new_index, true);
        return false;
    });

    /* register resize callback */
    jQuery(window).resize(function() { that.position_tour(); });
};

tl.pg.PageGuide.prototype.show_message = function (new_index, left) {
    var old_idx = this.cur_idx,
        old_item = this.$items[old_idx],
        new_item = this.$items[new_index];

    this.cur_idx = new_index;

    jQuery('div', this.$message).html(jQuery(new_item).children('div').html());
    this.$items.removeClass("tlypageguide-active");
    jQuery(new_item).addClass("tlypageguide-active");

    if (!tl.pg.isScrolledIntoView(jQuery(new_item))) {
        jQuery('html,body').animate({scrollTop: jQuery(new_item).offset().top - 50}, 500);
    }

    this.$message.not(':visible').show().animate({ 'height': '150px'}, 500);
    this.roll_number(jQuery('span', this.$message), jQuery(new_item).children('ins').html(), left);
};

tl.pg.PageGuide.prototype.roll_number = function (num_wrapper, new_text, left) {
    num_wrapper.animate({ 'text-indent': (left ? '' : '-') + '50px' }, 'fast', function() {
        num_wrapper.html(new_text);
        num_wrapper.css({ 'text-indent': (left ? '-' : '') + '50px' }, 'fast').animate({ 'text-indent': "0" }, 'fast');
    });
};

tl.pg.PageGuide.prototype.position_tour = function () {
    /* set PG element positions for visible tourtargets */
    this.$items = this.$all_items.filter(function () {
        return jQuery(jQuery(this).data('tourtarget')).is(':visible');
    });

    this.$items.each(function() {
        var arrow   = jQuery(this),
            target  = jQuery(arrow.data('tourtarget')).filter(':visible:first'),
            setLeft = target.offset().left,
            setTop  = target.offset().top;

        if (arrow.hasClass("tlypageguide_top")) {
            setTop -= 60;
        } else if (arrow.hasClass("tlypageguide_bottom")) {
            setTop += target.outerHeight() + 15;
        } else {
            setTop += 5;
        }

        if (arrow.hasClass("tlypageguide_right")) {
            setLeft += target.outerWidth(false) + 15;
        } else if (arrow.hasClass("tlypageguide_left")) {
            setLeft -= 65;
        } else {
            setLeft += 5;
        }

        arrow.css({ "left": setLeft + "px", "top": setTop + "px" });
    });
};
