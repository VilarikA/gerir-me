try {

	/*
	 * jQuery throttle / debounce - v1.1 - 3/7/2010
	 * http://benalman.com/projects/jquery-throttle-debounce-plugin/
	 *
	 * Dual licensed under the MIT and GPL licenses.
	 * http://benalman.com/about/license/
	 */
	(function(b, c) {
		var $ = b.jQuery || b.Cowboy || (b.Cowboy = {}),
			a;
		$.throttle = a = function(e, f, j, i) {
			var h, d = 0;
			if (typeof f !== "boolean") {
				i = j;
				j = f;
				f = c
			}

			function g() {
				var o = this,
					m = +new Date() - d,
					n = arguments;

				function l() {
					d = +new Date();
					j.apply(o, n)
				}

				function k() {
					h = c
				}
				if (i && !h) {
					l()
				}
				h && clearTimeout(h);
				if (i === c && m > e) {
					l()
				} else {
					if (f !== true) {
						h = setTimeout(i ? k : l, i === c ? e - m : e)
					}
				}
			}
			if ($.guid) {
				g.guid = j.guid = j.guid || $.guid++
			}
			return g
		};
		$.debounce = function(d, e, f) {
			return f === c ? a(d, e, false) : a(d, f, e !== false)
		}
	})(this);
	! function(a) {
		var b = /iPhone/i,
			c = /iPod/i,
			d = /iPad/i,
			e = /(?=.*\bAndroid\b)(?=.*\bMobile\b)/i,
			f = /Android/i,
			g = /IEMobile/i,
			h = /(?=.*\bWindows\b)(?=.*\bTouch\b)/i,
			i = /BlackBerry/i,
			j = /Opera Mini/i,
			k = /(?=.*\bFirefox\b)(?=.*\bMobile\b)/i,
			l = new RegExp("(?:Nexus 7|BNTV250|Kindle Fire|Silk|GT-P1000)", "i"),
			m = function(a, b) {
				return a.test(b)
			},
			n = function(a) {
				var n = a || navigator.userAgent;
				this.apple = {
					phone: m(b, n),
					ipod: m(c, n),
					tablet: m(d, n),
					device: m(b, n) || m(c, n) || m(d, n)
				}, this.android = {
					phone: m(e, n),
					tablet: !m(e, n) && m(f, n),
					device: m(e, n) || m(f, n)
				}, this.windows = {
					phone: m(g, n),
					tablet: m(h, n),
					device: m(g, n) || m(h, n)
				}, this.other = {
					blackberry: m(i, n),
					opera: m(j, n),
					firefox: m(k, n),
					device: m(i, n) || m(j, n) || m(k, n)
				}, this.seven_inch = m(l, n), this.any = this.apple.device || this.android.device || this.windows.device || this.other.device || this.seven_inch, this.phone = this.apple.phone || this.android.phone || this.windows.phone, this.tablet = this.apple.tablet || this.android.tablet || this.windows.tablet
			},
			o = new n;
		o.Class = n, "undefined" != typeof module && module.exports ? module.exports = o : "function" == typeof define && define.amd && define(o), a.isMobile = o
	}(this);
} catch (e) {
	console.log(e);
}
try {
	/*
	 * jquery.qtip. The jQuery tooltip plugin
	 *
	 * Copyright (c) 2009 Craig Thompson
	 * http://craigsworks.com
	 *
	 * Licensed under MIT
	 * http://www.opensource.org/licenses/mit-license.php
	 *
	 * Launch  : February 2009
	 * Version : 1.0.0-rc3
	 * Released: Tuesday 12th May, 2009 - 00:00
	 * Debug: jquery.qtip.debug.js
	 */
	(function(f) {
		f.fn.qtip = function(B, u) {
			var y, t, A, s, x, w, v, z;
			if (typeof B == "string") {
				if (typeof f(this).data("qtip") !== "object") {
					f.fn.qtip.log.error.call(self, 1, f.fn.qtip.constants.NO_TOOLTIP_PRESENT, false)
				}
				if (B == "api") {
					return f(this).data("qtip").interfaces[f(this).data("qtip").current]
				} else {
					if (B == "interfaces") {
						return f(this).data("qtip").interfaces
					}
				}
			} else {
				if (!B) {
					B = {}
				}
				if (typeof B.content !== "object" || (B.content.jquery && B.content.length > 0)) {
					B.content = {
						text: B.content
					}
				}
				if (typeof B.content.title !== "object") {
					B.content.title = {
						text: B.content.title
					}
				}
				if (typeof B.position !== "object") {
					B.position = {
						corner: B.position
					}
				}
				if (typeof B.position.corner !== "object") {
					B.position.corner = {
						target: B.position.corner,
						tooltip: B.position.corner
					}
				}
				if (typeof B.show !== "object") {
					B.show = {
						when: B.show
					}
				}
				if (typeof B.show.when !== "object") {
					B.show.when = {
						event: B.show.when
					}
				}
				if (typeof B.show.effect !== "object") {
					B.show.effect = {
						type: B.show.effect
					}
				}
				if (typeof B.hide !== "object") {
					B.hide = {
						when: B.hide
					}
				}
				if (typeof B.hide.when !== "object") {
					B.hide.when = {
						event: B.hide.when
					}
				}
				if (typeof B.hide.effect !== "object") {
					B.hide.effect = {
						type: B.hide.effect
					}
				}
				if (typeof B.style !== "object") {
					B.style = {
						name: B.style
					}
				}
				B.style = c(B.style);
				s = f.extend(true, {}, f.fn.qtip.defaults, B);
				s.style = a.call({
					options: s
				}, s.style);
				s.user = f.extend(true, {}, B)
			}
			return f(this).each(function() {
				if (typeof B == "string") {
					w = B.toLowerCase();
					A = f(this).qtip("interfaces");
					if (typeof A == "object") {
						if (u === true && w == "destroy") {
							while (A.length > 0) {
								A[A.length - 1].destroy()
							}
						} else {
							if (u !== true) {
								A = [f(this).qtip("api")]
							}
							for (y = 0; y < A.length; y++) {
								if (w == "destroy") {
									A[y].destroy()
								} else {
									if (A[y].status.rendered === true) {
										if (w == "show") {
											A[y].show()
										} else {
											if (w == "hide") {
												A[y].hide()
											} else {
												if (w == "focus") {
													A[y].focus()
												} else {
													if (w == "disable") {
														A[y].disable(true)
													} else {
														if (w == "enable") {
															A[y].disable(false)
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				} else {
					v = f.extend(true, {}, s);
					v.hide.effect.length = s.hide.effect.length;
					v.show.effect.length = s.show.effect.length;
					if (v.position.container === false) {
						v.position.container = f(document.body)
					}
					if (v.position.target === false) {
						v.position.target = f(this)
					}
					if (v.show.when.target === false) {
						v.show.when.target = f(this)
					}
					if (v.hide.when.target === false) {
						v.hide.when.target = f(this)
					}
					t = f.fn.qtip.interfaces.length;
					for (y = 0; y < t; y++) {
						if (typeof f.fn.qtip.interfaces[y] == "undefined") {
							t = y;
							break
						}
					}
					x = new d(f(this), v, t);
					f.fn.qtip.interfaces[t] = x;
					if (typeof f(this).data("qtip") == "object") {
						if (typeof f(this).attr("qtip") === "undefined") {
							f(this).data("qtip").current = f(this).data("qtip").interfaces.length
						}
						f(this).data("qtip").interfaces.push(x)
					} else {
						f(this).data("qtip", {
							current: 0,
							interfaces: [x]
						})
					}
					if (v.content.prerender === false && v.show.when.event !== false && v.show.ready !== true) {
						v.show.when.target.bind(v.show.when.event + ".qtip-" + t + "-create", {
							qtip: t
						}, function(C) {
							z = f.fn.qtip.interfaces[C.data.qtip];
							z.options.show.when.target.unbind(z.options.show.when.event + ".qtip-" + C.data.qtip + "-create");
							z.cache.mouse = {
								x: C.pageX,
								y: C.pageY
							};
							p.call(z);
							z.options.show.when.target.trigger(z.options.show.when.event)
						})
					} else {
						x.cache.mouse = {
							x: v.show.when.target.offset().left,
							y: v.show.when.target.offset().top
						};
						p.call(x)
					}
				}
			})
		};

		function d(u, t, v) {
			var s = this;
			s.id = v;
			s.options = t;
			s.status = {
				animated: false,
				rendered: false,
				disabled: false,
				focused: false
			};
			s.elements = {
				target: u.addClass(s.options.style.classes.target),
				tooltip: null,
				wrapper: null,
				content: null,
				contentWrapper: null,
				title: null,
				button: null,
				tip: null,
				bgiframe: null
			};
			s.cache = {
				mouse: {},
				position: {},
				toggle: 0
			};
			s.timers = {};
			f.extend(s, s.options.api, {
				show: function(y) {
					var x, z;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "show")
					}
					if (s.elements.tooltip.css("display") !== "none") {
						return s
					}
					s.elements.tooltip.stop(true, false);
					x = s.beforeShow.call(s, y);
					if (x === false) {
						return s
					}

					function w() {
						if (s.options.position.type !== "static") {
							s.focus()
						}
						s.onShow.call(s, y);
						if (f.browser && f.browser.msie) {
							s.elements.tooltip.get(0).style.removeAttribute("filter")
						}
					}
					s.cache.toggle = 1;
					if (s.options.position.type !== "static") {
						s.updatePosition(y, (s.options.show.effect.length > 0))
					}
					if (typeof s.options.show.solo == "object") {
						z = f(s.options.show.solo)
					} else {
						if (s.options.show.solo === true) {
							z = f("div.qtip").not(s.elements.tooltip)
						}
					}
					if (z) {
						z.each(function() {
							if (f(this).qtip("api").status.rendered === true) {
								f(this).qtip("api").hide()
							}
						})
					}
					if (typeof s.options.show.effect.type == "function") {
						s.options.show.effect.type.call(s.elements.tooltip, s.options.show.effect.length);
						s.elements.tooltip.queue(function() {
							w();
							f(this).dequeue()
						})
					} else {
						switch (s.options.show.effect.type.toLowerCase()) {
							case "fade":
								s.elements.tooltip.fadeIn(s.options.show.effect.length, w);
								break;
							case "slide":
								s.elements.tooltip.slideDown(s.options.show.effect.length, function() {
									w();
									if (s.options.position.type !== "static") {
										s.updatePosition(y, true)
									}
								});
								break;
							case "grow":
								s.elements.tooltip.show(s.options.show.effect.length, w);
								break;
							default:
								s.elements.tooltip.show(null, w);
								break
						}
						s.elements.tooltip.addClass(s.options.style.classes.active)
					}
					return f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_SHOWN, "show")
				},
				hide: function(y) {
					var x;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "hide")
					} else {
						if (s.elements.tooltip.css("display") === "none") {
							return s
						}
					}
					clearTimeout(s.timers.show);
					s.elements.tooltip.stop(true, false);
					x = s.beforeHide.call(s, y);
					if (x === false) {
						return s
					}

					function w() {
						s.onHide.call(s, y)
					}
					s.cache.toggle = 0;
					if (typeof s.options.hide.effect.type == "function") {
						s.options.hide.effect.type.call(s.elements.tooltip, s.options.hide.effect.length);
						s.elements.tooltip.queue(function() {
							w();
							f(this).dequeue()
						})
					} else {
						switch (s.options.hide.effect.type.toLowerCase()) {
							case "fade":
								s.elements.tooltip.fadeOut(s.options.hide.effect.length, w);
								break;
							case "slide":
								s.elements.tooltip.slideUp(s.options.hide.effect.length, w);
								break;
							case "grow":
								s.elements.tooltip.hide(s.options.hide.effect.length, w);
								break;
							default:
								s.elements.tooltip.hide(null, w);
								break
						}
						s.elements.tooltip.removeClass(s.options.style.classes.active)
					}
					return f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_HIDDEN, "hide")
				},
				updatePosition: function(w, x) {
					var C, G, L, J, H, E, y, I, B, D, K, A, F, z;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "updatePosition")
					} else {
						if (s.options.position.type == "static") {
							return f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.CANNOT_POSITION_STATIC, "updatePosition")
						}
					}
					G = {
						position: {
							left: 0,
							top: 0
						},
						dimensions: {
							height: 0,
							width: 0
						},
						corner: s.options.position.corner.target
					};
					L = {
						position: s.getPosition(),
						dimensions: s.getDimensions(),
						corner: s.options.position.corner.tooltip
					};
					if (s.options.position.target !== "mouse") {
						if (s.options.position.target.get(0).nodeName.toLowerCase() == "area") {
							J = s.options.position.target.attr("coords").split(",");
							for (C = 0; C < J.length; C++) {
								J[C] = parseInt(J[C])
							}
							H = s.options.position.target.parent("map").attr("name");
							E = f('img[usemap="#' + H + '"]:first').offset();
							G.position = {
								left: Math.floor(E.left + J[0]),
								top: Math.floor(E.top + J[1])
							};
							switch (s.options.position.target.attr("shape").toLowerCase()) {
								case "rect":
									G.dimensions = {
										width: Math.ceil(Math.abs(J[2] - J[0])),
										height: Math.ceil(Math.abs(J[3] - J[1]))
									};
									break;
								case "circle":
									G.dimensions = {
										width: J[2] + 1,
										height: J[2] + 1
									};
									break;
								case "poly":
									G.dimensions = {
										width: J[0],
										height: J[1]
									};
									for (C = 0; C < J.length; C++) {
										if (C % 2 == 0) {
											if (J[C] > G.dimensions.width) {
												G.dimensions.width = J[C]
											}
											if (J[C] < J[0]) {
												G.position.left = Math.floor(E.left + J[C])
											}
										} else {
											if (J[C] > G.dimensions.height) {
												G.dimensions.height = J[C]
											}
											if (J[C] < J[1]) {
												G.position.top = Math.floor(E.top + J[C])
											}
										}
									}
									G.dimensions.width = G.dimensions.width - (G.position.left - E.left);
									G.dimensions.height = G.dimensions.height - (G.position.top - E.top);
									break;
								default:
									return f.fn.qtip.log.error.call(s, 4, f.fn.qtip.constants.INVALID_AREA_SHAPE, "updatePosition");
									break
							}
							G.dimensions.width -= 2;
							G.dimensions.height -= 2
						} else {
							if (s.options.position.target.add(document.body).length === 1) {
								G.position = {
									left: f(document).scrollLeft(),
									top: f(document).scrollTop()
								};
								G.dimensions = {
									height: f(window).height(),
									width: f(window).width()
								}
							} else {
								if (typeof s.options.position.target.attr("qtip") !== "undefined") {
									G.position = s.options.position.target.qtip("api").cache.position
								} else {
									G.position = s.options.position.target.offset()
								}
								G.dimensions = {
									height: s.options.position.target.outerHeight(),
									width: s.options.position.target.outerWidth()
								}
							}
						}
						y = f.extend({}, G.position);
						if (G.corner.search(/right/i) !== -1) {
							y.left += G.dimensions.width
						}
						if (G.corner.search(/bottom/i) !== -1) {
							y.top += G.dimensions.height
						}
						if (G.corner.search(/((top|bottom)Middle)|center/) !== -1) {
							y.left += (G.dimensions.width / 2)
						}
						if (G.corner.search(/((left|right)Middle)|center/) !== -1) {
							y.top += (G.dimensions.height / 2)
						}
					} else {
						G.position = y = {
							left: s.cache.mouse.x,
							top: s.cache.mouse.y
						};
						G.dimensions = {
							height: 1,
							width: 1
						}
					}
					if (L.corner.search(/right/i) !== -1) {
						y.left -= L.dimensions.width
					}
					if (L.corner.search(/bottom/i) !== -1) {
						y.top -= L.dimensions.height
					}
					if (L.corner.search(/((top|bottom)Middle)|center/) !== -1) {
						y.left -= (L.dimensions.width / 2)
					}
					if (L.corner.search(/((left|right)Middle)|center/) !== -1) {
						y.top -= (L.dimensions.height / 2)
					}
					I = (f.browser && f.browser.msie) ? 1 : 0;
					B = (f.browser && f.browser.msie && parseInt(f.browser.version.charAt(0)) === 6) ? 1 : 0;
					if (s.options.style.border.radius > 0) {
						if (L.corner.search(/Left/) !== -1) {
							y.left -= s.options.style.border.radius
						} else {
							if (L.corner.search(/Right/) !== -1) {
								y.left += s.options.style.border.radius
							}
						}
						if (L.corner.search(/Top/) !== -1) {
							y.top -= s.options.style.border.radius
						} else {
							if (L.corner.search(/Bottom/) !== -1) {
								y.top += s.options.style.border.radius
							}
						}
					}
					if (I) {
						if (L.corner.search(/top/) !== -1) {
							y.top -= I
						} else {
							if (L.corner.search(/bottom/) !== -1) {
								y.top += I
							}
						}
						if (L.corner.search(/left/) !== -1) {
							y.left -= I
						} else {
							if (L.corner.search(/right/) !== -1) {
								y.left += I
							}
						}
						if (L.corner.search(/leftMiddle|rightMiddle/) !== -1) {
							y.top -= 1
						}
					}
					if (s.options.position.adjust.screen === true) {
						y = o.call(s, y, G, L)
					}
					if (s.options.position.target === "mouse" && s.options.position.adjust.mouse === true) {
						if (s.options.position.adjust.screen === true && s.elements.tip) {
							K = s.elements.tip.attr("rel")
						} else {
							K = s.options.position.corner.tooltip
						}
						y.left += (K.search(/right/i) !== -1) ? -6 : 6;
						y.top += (K.search(/bottom/i) !== -1) ? -6 : 6
					}
					if (!s.elements.bgiframe && f.browser &&  f.browser.msie && parseInt(f.browser.version.charAt(0)) == 6) {
						f("select, object").each(function() {
							A = f(this).offset();
							A.bottom = A.top + f(this).height();
							A.right = A.left + f(this).width();
							if (y.top + L.dimensions.height >= A.top && y.left + L.dimensions.width >= A.left) {
								k.call(s)
							}
						})
					}
					y.left += s.options.position.adjust.x;
					y.top += s.options.position.adjust.y;
					F = s.getPosition();
					if (y.left != F.left || y.top != F.top) {
						z = s.beforePositionUpdate.call(s, w);
						if (z === false) {
							return s
						}
						s.cache.position = y;
						if (x === true) {
							s.status.animated = true;
							s.elements.tooltip.animate(y, 200, "swing", function() {
								s.status.animated = false
							})
						} else {
							s.elements.tooltip.css(y)
						}
						s.onPositionUpdate.call(s, w);
						if (typeof w !== "undefined" && w.type && w.type !== "mousemove") {
							f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_POSITION_UPDATED, "updatePosition")
						}
					}
					return s
				},
				updateWidth: function(w) {
					var x;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "updateWidth")
					} else {
						if (w && typeof w !== "number") {
							return f.fn.qtip.log.error.call(s, 2, "newWidth must be of type number", "updateWidth")
						}
					}
					x = s.elements.contentWrapper.siblings().add(s.elements.tip).add(s.elements.button);
					if (!w) {
						if (typeof s.options.style.width.value == "number") {
							w = s.options.style.width.value
						} else {
							s.elements.tooltip.css({
								width: "auto"
							});
							x.hide();
							if (f.browser && f.browser.msie) {
								s.elements.wrapper.add(s.elements.contentWrapper.children()).css({
									zoom: "normal"
								})
							}
							w = s.getDimensions().width + 1;
							if (!s.options.style.width.value) {
								if (w > s.options.style.width.max) {
									w = s.options.style.width.max
								}
								if (w < s.options.style.width.min) {
									w = s.options.style.width.min
								}
							}
						}
					}
					if (w % 2 !== 0) {
						w -= 1
					}
					s.elements.tooltip.width(w);
					x.show();
					if (s.options.style.border.radius) {
						s.elements.tooltip.find(".qtip-betweenCorners").each(function(y) {
							f(this).width(w - (s.options.style.border.radius * 2))
						})
					}
					if (f.browser && f.browser.msie) {
						s.elements.wrapper.add(s.elements.contentWrapper.children()).css({
							zoom: "1"
						});
						s.elements.wrapper.width(w);
						if (s.elements.bgiframe) {
							s.elements.bgiframe.width(w).height(s.getDimensions.height)
						}
					}
					return f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_WIDTH_UPDATED, "updateWidth")
				},
				updateStyle: function(w) {
					var z, A, x, y, B;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "updateStyle")
					} else {
						if (typeof w !== "string" || !f.fn.qtip.styles[w]) {
							return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.STYLE_NOT_DEFINED, "updateStyle")
						}
					}
					s.options.style = a.call(s, f.fn.qtip.styles[w], s.options.user.style);
					s.elements.content.css(q(s.options.style));
					if (s.options.content.title.text !== false) {
						s.elements.title.css(q(s.options.style.title, true))
					}
					s.elements.contentWrapper.css({
						borderColor: s.options.style.border.color
					});
					if (s.options.style.tip.corner !== false) {
						if (f("<canvas>").get(0).getContext) {
							z = s.elements.tooltip.find(".qtip-tip canvas:first");
							x = z.get(0).getContext("2d");
							x.clearRect(0, 0, 300, 300);
							y = z.parent("div[rel]:first").attr("rel");
							B = b(y, s.options.style.tip.size.width, s.options.style.tip.size.height);
							h.call(s, z, B, s.options.style.tip.color || s.options.style.border.color)
						} else {
							if (f.browser && f.browser.msie) {
								z = s.elements.tooltip.find('.qtip-tip [nodeName="shape"]');
								z.attr("fillcolor", s.options.style.tip.color || s.options.style.border.color)
							}
						}
					}
					if (s.options.style.border.radius > 0) {
						s.elements.tooltip.find(".qtip-betweenCorners").css({
							backgroundColor: s.options.style.border.color
						});
						if (f("<canvas>").get(0).getContext) {
							A = g(s.options.style.border.radius);
							s.elements.tooltip.find(".qtip-wrapper canvas").each(function() {
								x = f(this).get(0).getContext("2d");
								x.clearRect(0, 0, 300, 300);
								y = f(this).parent("div[rel]:first").attr("rel");
								r.call(s, f(this), A[y], s.options.style.border.radius, s.options.style.border.color)
							})
						} else {
							if (f.browser && f.browser.msie) {
								s.elements.tooltip.find('.qtip-wrapper [nodeName="arc"]').each(function() {
									f(this).attr("fillcolor", s.options.style.border.color)
								})
							}
						}
					}
					return f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_STYLE_UPDATED, "updateStyle")
				},
				updateContent: function(A, y) {
					var z, x, w;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "updateContent")
					} else {
						if (!A) {
							return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.NO_CONTENT_PROVIDED, "updateContent")
						}
					}
					z = s.beforeContentUpdate.call(s, A);
					if (typeof z == "string") {
						A = z
					} else {
						if (z === false) {
							return
						}
					}
					if (f.browser && f.browser.msie) {
						s.elements.contentWrapper.children().css({
							zoom: "normal"
						})
					}
					if (A.jquery && A.length > 0) {
						A.clone(true).appendTo(s.elements.content).show()
					} else {
						s.elements.content.html(A)
					}
					x = s.elements.content.find("img[complete=false]");
					if (x.length > 0) {
						w = 0;
						x.each(function(C) {
							f('<img src="' + f(this).attr("src") + '" />').load(function() {
								if (++w == x.length) {
									B()
								}
							})
						})
					} else {
						B()
					}

					function B() {
						s.updateWidth();
						if (y !== false) {
							if (s.options.position.type !== "static") {
								s.updatePosition(s.elements.tooltip.is(":visible"), true)
							}
							if (s.options.style.tip.corner !== false) {
								n.call(s)
							}
						}
					}
					s.onContentUpdate.call(s);
					return f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_CONTENT_UPDATED, "loadContent")
				},
				loadContent: function(w, z, A) {
					var y;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "loadContent")
					}
					y = s.beforeContentLoad.call(s);
					if (y === false) {
						return s
					}
					if (A == "post") {
						f.post(w, z, x)
					} else {
						f.get(w, z, x)
					}

					function x(B) {
						s.onContentLoad.call(s);
						f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_CONTENT_LOADED, "loadContent");
						s.updateContent(B)
					}
					return s
				},
				updateTitle: function(w) {
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "updateTitle")
					} else {
						if (!w) {
							return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.NO_CONTENT_PROVIDED, "updateTitle")
						}
					}
					returned = s.beforeTitleUpdate.call(s);
					if (returned === false) {
						return s
					}
					if (s.elements.button) {
						s.elements.button = s.elements.button.clone(true)
					}
					s.elements.title.html(w);
					if (s.elements.button) {
						s.elements.title.prepend(s.elements.button)
					}
					s.onTitleUpdate.call(s);
					return f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_TITLE_UPDATED, "updateTitle")
				},
				focus: function(A) {
					var y, x, w, z;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "focus")
					} else {
						if (s.options.position.type == "static") {
							return f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.CANNOT_FOCUS_STATIC, "focus")
						}
					}
					y = parseInt(s.elements.tooltip.css("z-index"));
					x = 6000 + f("div.qtip[qtip]").length - 1;
					if (!s.status.focused && y !== x) {
						z = s.beforeFocus.call(s, A);
						if (z === false) {
							return s
						}
						f("div.qtip[qtip]").not(s.elements.tooltip).each(function() {
							if (f(this).qtip("api").status.rendered === true) {
								w = parseInt(f(this).css("z-index"));
								if (typeof w == "number" && w > -1) {
									f(this).css({
										zIndex: parseInt(f(this).css("z-index")) - 1
									})
								}
								f(this).qtip("api").status.focused = false
							}
						});
						s.elements.tooltip.css({
							zIndex: x
						});
						s.status.focused = true;
						s.onFocus.call(s, A);
						f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_FOCUSED, "focus")
					}
					return s
				},
				disable: function(w) {
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "disable")
					}
					if (w) {
						if (!s.status.disabled) {
							s.status.disabled = true;
							f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_DISABLED, "disable")
						} else {
							f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.TOOLTIP_ALREADY_DISABLED, "disable")
						}
					} else {
						if (s.status.disabled) {
							s.status.disabled = false;
							f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_ENABLED, "disable")
						} else {
							f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.TOOLTIP_ALREADY_ENABLED, "disable")
						}
					}
					return s
				},
				destroy: function() {
					var w, x, y;
					x = s.beforeDestroy.call(s);
					if (x === false) {
						return s
					}
					if (s.status.rendered) {
						s.options.show.when.target.unbind("mousemove.qtip", s.updatePosition);
						s.options.show.when.target.unbind("mouseout.qtip", s.hide);
						s.options.show.when.target.unbind(s.options.show.when.event + ".qtip");
						s.options.hide.when.target.unbind(s.options.hide.when.event + ".qtip");
						s.elements.tooltip.unbind(s.options.hide.when.event + ".qtip");
						s.elements.tooltip.unbind("mouseover.qtip", s.focus);
						s.elements.tooltip.remove()
					} else {
						s.options.show.when.target.unbind(s.options.show.when.event + ".qtip-create")
					}
					if (typeof s.elements.target.data("qtip") == "object") {
						y = s.elements.target.data("qtip").interfaces;
						if (typeof y == "object" && y.length > 0) {
							for (w = 0; w < y.length - 1; w++) {
								if (y[w].id == s.id) {
									y.splice(w, 1)
								}
							}
						}
					}
					delete f.fn.qtip.interfaces[s.id];
					if (typeof y == "object" && y.length > 0) {
						s.elements.target.data("qtip").current = y.length - 1
					} else {
						s.elements.target.removeData("qtip")
					}
					s.onDestroy.call(s);
					f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_DESTROYED, "destroy");
					return s.elements.target
				},
				getPosition: function() {
					var w, x;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "getPosition")
					}
					w = (s.elements.tooltip.css("display") !== "none") ? false : true;
					if (w) {
						s.elements.tooltip.css({
							visiblity: "hidden"
						}).show()
					}
					x = s.elements.tooltip.offset();
					if (w) {
						s.elements.tooltip.css({
							visiblity: "visible"
						}).hide()
					}
					return x
				},
				getDimensions: function() {
					var w, x;
					if (!s.status.rendered) {
						return f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.TOOLTIP_NOT_RENDERED, "getDimensions")
					}
					w = (!s.elements.tooltip.is(":visible")) ? true : false;
					if (w) {
						s.elements.tooltip.css({
							visiblity: "hidden"
						}).show()
					}
					x = {
						height: s.elements.tooltip.outerHeight(),
						width: s.elements.tooltip.outerWidth()
					};
					if (w) {
						s.elements.tooltip.css({
							visiblity: "visible"
						}).hide()
					}
					return x
				}
			})
		}

		function p() {
			var s, w, u, t, v, y, x;
			s = this;
			s.beforeRender.call(s);
			s.status.rendered = true;
			s.elements.tooltip = '<div qtip="' + s.id + '" class="qtip ' + (s.options.style.classes.tooltip || s.options.style) + '"style="display:none; -moz-border-radius:0; -webkit-border-radius:0; border-radius:0;position:' + s.options.position.type + ';">  <div class="qtip-wrapper" style="position:relative; overflow:hidden; text-align:left;">    <div class="qtip-contentWrapper" style="overflow:hidden;">       <div class="qtip-content ' + s.options.style.classes.content + '"></div></div></div></div>';
			s.elements.tooltip = f(s.elements.tooltip);
			s.elements.tooltip.appendTo(s.options.position.container);
			s.elements.tooltip.data("qtip", {
				current: 0,
				interfaces: [s]
			});
			s.elements.wrapper = s.elements.tooltip.children("div:first");
			s.elements.contentWrapper = s.elements.wrapper.children("div:first").css({
				background: s.options.style.background
			});
			s.elements.content = s.elements.contentWrapper.children("div:first").css(q(s.options.style));
			if (f.browser && f.browser.msie) {
				s.elements.wrapper.add(s.elements.content).css({
					zoom: 1
				})
			}
			if (s.options.hide.when.event == "unfocus") {
				s.elements.tooltip.attr("unfocus", true)
			}
			if (typeof s.options.style.width.value == "number") {
				s.updateWidth()
			}
			if (f("<canvas>").get(0).getContext || (f.browser && f.browser.msie)) {
				if (s.options.style.border.radius > 0) {
					m.call(s)
				} else {
					s.elements.contentWrapper.css({
						border: s.options.style.border.width + "px solid " + s.options.style.border.color
					})
				}
				if (s.options.style.tip.corner !== false) {
					e.call(s)
				}
			} else {
				s.elements.contentWrapper.css({
					border: s.options.style.border.width + "px solid " + s.options.style.border.color
				});
				s.options.style.border.radius = 0;
				s.options.style.tip.corner = false;
				f.fn.qtip.log.error.call(s, 2, f.fn.qtip.constants.CANVAS_VML_NOT_SUPPORTED, "render")
			}
			if ((typeof s.options.content.text == "string" && s.options.content.text.length > 0) || (s.options.content.text.jquery && s.options.content.text.length > 0)) {
				u = s.options.content.text
			} else {
				if (typeof s.elements.target.attr("title") == "string" && s.elements.target.attr("title").length > 0) {
					u = s.elements.target.attr("title").replace("\\n", "<br />");
					s.elements.target.attr("title", "")
				} else {
					if (typeof s.elements.target.attr("alt") == "string" && s.elements.target.attr("alt").length > 0) {
						u = s.elements.target.attr("alt").replace("\\n", "<br />");
						s.elements.target.attr("alt", "")
					} else {
						u = " ";
						f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.NO_VALID_CONTENT, "render")
					}
				}
			}
			if (s.options.content.title.text !== false) {
				j.call(s)
			}
			s.updateContent(u);
			l.call(s);
			if (s.options.show.ready === true) {
				s.show()
			}
			if (s.options.content.url !== false) {
				t = s.options.content.url;
				v = s.options.content.data;
				y = s.options.content.method || "get";
				s.loadContent(t, v, y)
			}
			s.onRender.call(s);
			f.fn.qtip.log.error.call(s, 1, f.fn.qtip.constants.EVENT_RENDERED, "render")
		}

		function m() {
			var F, z, t, B, x, E, u, G, D, y, w, C, A, s, v;
			F = this;
			F.elements.wrapper.find(".qtip-borderBottom, .qtip-borderTop").remove();
			t = F.options.style.border.width;
			B = F.options.style.border.radius;
			x = F.options.style.border.color || F.options.style.tip.color;
			E = g(B);
			u = {};
			for (z in E) {
				u[z] = '<div rel="' + z + '" style="' + ((z.search(/Left/) !== -1) ? "left" : "right") + ":0; position:absolute; height:" + B + "px; width:" + B + 'px; overflow:hidden; line-height:0.1px; font-size:1px">';
				if (f("<canvas>").get(0).getContext) {
					u[z] += '<canvas height="' + B + '" width="' + B + '" style="vertical-align: top"></canvas>'
				} else {
					if (f.browser && f.browser.msie) {
						G = B * 2 + 3;
						u[z] += '<v:arc stroked="false" fillcolor="' + x + '" startangle="' + E[z][0] + '" endangle="' + E[z][1] + '" style="width:' + G + "px; height:" + G + "px; margin-top:" + ((z.search(/bottom/) !== -1) ? -2 : -1) + "px; margin-left:" + ((z.search(/Right/) !== -1) ? E[z][2] - 3.5 : -1) + 'px; vertical-align:top; display:inline-block; behavior:url(#default#VML)"></v:arc>'
					}
				}
				u[z] += "</div>"
			}
			D = F.getDimensions().width - (Math.max(t, B) * 2);
			y = '<div class="qtip-betweenCorners" style="height:' + B + "px; width:" + D + "px; overflow:hidden; background-color:" + x + '; line-height:0.1px; font-size:1px;">';
			w = '<div class="qtip-borderTop" dir="ltr" style="height:' + B + "px; margin-left:" + B + 'px; line-height:0.1px; font-size:1px; padding:0;">' + u.topLeft + u.topRight + y;
			F.elements.wrapper.prepend(w);
			C = '<div class="qtip-borderBottom" dir="ltr" style="height:' + B + "px; margin-left:" + B + 'px; line-height:0.1px; font-size:1px; padding:0;">' + u.bottomLeft + u.bottomRight + y;
			F.elements.wrapper.append(C);
			if (f("<canvas>").get(0).getContext) {
				F.elements.wrapper.find("canvas").each(function() {
					A = E[f(this).parent("[rel]:first").attr("rel")];
					r.call(F, f(this), A, B, x)
				})
			} else {
				if (f.browser && f.browser.msie) {
					F.elements.tooltip.append('<v:image style="behavior:url(#default#VML);"></v:image>')
				}
			}
			s = Math.max(B, (B + (t - B)));
			v = Math.max(t - B, 0);
			F.elements.contentWrapper.css({
				border: "0px solid " + x,
				borderWidth: v + "px " + s + "px"
			})
		}

		function r(u, w, s, t) {
			var v = u.get(0).getContext("2d");
			v.fillStyle = t;
			v.beginPath();
			v.arc(w[0], w[1], s, 0, Math.PI * 2, false);
			v.fill()
		}

		function e(v) {
			var t, s, x, u, w;
			t = this;
			if (t.elements.tip !== null) {
				t.elements.tip.remove()
			}
			s = t.options.style.tip.color || t.options.style.border.color;
			if (t.options.style.tip.corner === false) {
				return
			} else {
				if (!v) {
					v = t.options.style.tip.corner
				}
			}
			x = b(v, t.options.style.tip.size.width, t.options.style.tip.size.height);
			t.elements.tip = '<div class="' + t.options.style.classes.tip + '" dir="ltr" rel="' + v + '" style="position:absolute; height:' + t.options.style.tip.size.height + "px; width:" + t.options.style.tip.size.width + 'px; margin:0 auto; line-height:0.1px; font-size:1px;">';
			if (f("<canvas>").get(0).getContext) {
				t.elements.tip += '<canvas height="' + t.options.style.tip.size.height + '" width="' + t.options.style.tip.size.width + '"></canvas>'
			} else {
				if (f.browser && f.browser.msie) {
					u = t.options.style.tip.size.width + "," + t.options.style.tip.size.height;
					w = "m" + x[0][0] + "," + x[0][1];
					w += " l" + x[1][0] + "," + x[1][1];
					w += " " + x[2][0] + "," + x[2][1];
					w += " xe";
					t.elements.tip += '<v:shape fillcolor="' + s + '" stroked="false" filled="true" path="' + w + '" coordsize="' + u + '" style="width:' + t.options.style.tip.size.width + "px; height:" + t.options.style.tip.size.height + "px; line-height:0.1px; display:inline-block; behavior:url(#default#VML); vertical-align:" + ((v.search(/top/) !== -1) ? "bottom" : "top") + '"></v:shape>';
					t.elements.tip += '<v:image style="behavior:url(#default#VML);"></v:image>';
					t.elements.contentWrapper.css("position", "relative")
				}
			}
			t.elements.tooltip.prepend(t.elements.tip + "</div>");
			t.elements.tip = t.elements.tooltip.find("." + t.options.style.classes.tip).eq(0);
			if (f("<canvas>").get(0).getContext) {
				h.call(t, t.elements.tip.find("canvas:first"), x, s)
			}
			if (v.search(/top/) !== -1 && f.browser && f.browser.msie && parseInt(f.browser.version.charAt(0)) === 6) {
				t.elements.tip.css({
					marginTop: -4
				})
			}
			n.call(t, v)
		}

		function h(t, v, s) {
			var u = t.get(0).getContext("2d");
			u.fillStyle = s;
			u.beginPath();
			u.moveTo(v[0][0], v[0][1]);
			u.lineTo(v[1][0], v[1][1]);
			u.lineTo(v[2][0], v[2][1]);
			u.fill()
		}

		function n(u) {
			var t, w, s, x, v;
			t = this;
			if (t.options.style.tip.corner === false || !t.elements.tip) {
				return
			}
			if (!u) {
				u = t.elements.tip.attr("rel")
			}
			w = positionAdjust = (f.browser && f.browser.msie) ? 1 : 0;
			t.elements.tip.css(u.match(/left|right|top|bottom/)[0], 0);
			if (u.search(/top|bottom/) !== -1) {
				if (f.browser && f.browser.msie) {
					if (parseInt(f.browser.version.charAt(0)) === 6) {
						positionAdjust = (u.search(/top/) !== -1) ? -3 : 1
					} else {
						positionAdjust = (u.search(/top/) !== -1) ? 1 : 2
					}
				}
				if (u.search(/Middle/) !== -1) {
					t.elements.tip.css({
						left: "50%",
						marginLeft: -(t.options.style.tip.size.width / 2)
					})
				} else {
					if (u.search(/Left/) !== -1) {
						t.elements.tip.css({
							left: t.options.style.border.radius - w
						})
					} else {
						if (u.search(/Right/) !== -1) {
							t.elements.tip.css({
								right: t.options.style.border.radius + w
							})
						}
					}
				}
				if (u.search(/top/) !== -1) {
					t.elements.tip.css({
						top: -positionAdjust
					})
				} else {
					t.elements.tip.css({
						bottom: positionAdjust
					})
				}
			} else {
				if (u.search(/left|right/) !== -1) {
					if (f.browser && f.browser.msie) {
						positionAdjust = (parseInt(f.browser.version.charAt(0)) === 6) ? 1 : ((u.search(/left/) !== -1) ? 1 : 2)
					}
					if (u.search(/Middle/) !== -1) {
						t.elements.tip.css({
							top: "50%",
							marginTop: -(t.options.style.tip.size.height / 2)
						})
					} else {
						if (u.search(/Top/) !== -1) {
							t.elements.tip.css({
								top: t.options.style.border.radius - w
							})
						} else {
							if (u.search(/Bottom/) !== -1) {
								t.elements.tip.css({
									bottom: t.options.style.border.radius + w
								})
							}
						}
					}
					if (u.search(/left/) !== -1) {
						t.elements.tip.css({
							left: -positionAdjust
						})
					} else {
						t.elements.tip.css({
							right: positionAdjust
						})
					}
				}
			}
			s = "padding-" + u.match(/left|right|top|bottom/)[0];
			x = t.options.style.tip.size[(s.search(/left|right/) !== -1) ? "width" : "height"];
			t.elements.tooltip.css("padding", 0);
			t.elements.tooltip.css(s, x);
			if (f.browser && f.browser.msie && parseInt(f.browser.version.charAt(0)) == 6) {
				v = parseInt(t.elements.tip.css("margin-top")) || 0;
				v += parseInt(t.elements.content.css("margin-top")) || 0;
				t.elements.tip.css({
					marginTop: v
				})
			}
		}

		function j() {
			var s = this;
			if (s.elements.title !== null) {
				s.elements.title.remove()
			}
			s.elements.title = f('<div class="' + s.options.style.classes.title + '">').css(q(s.options.style.title, true)).css({
				zoom: (f.browser && f.browser.msie) ? 1 : 0
			}).prependTo(s.elements.contentWrapper);
			if (s.options.content.title.text) {
				s.updateTitle.call(s, s.options.content.title.text)
			}
			if (s.options.content.title.button !== false && typeof s.options.content.title.button == "string") {
				s.elements.button = f('<a class="' + s.options.style.classes.button + '" style="float:right; position: relative"></a>').css(q(s.options.style.button, true)).html(s.options.content.title.button).prependTo(s.elements.title).click(function(t) {
					if (!s.status.disabled) {
						s.hide(t)
					}
				})
			}
		}

		function l() {
			var t, v, u, s;
			t = this;
			v = t.options.show.when.target;
			u = t.options.hide.when.target;
			if (t.options.hide.fixed) {
				u = u.add(t.elements.tooltip)
			}
			if (t.options.hide.when.event == "inactive") {
				s = ["click", "dblclick", "mousedown", "mouseup", "mousemove", "mouseout", "mouseenter", "mouseleave", "mouseover"];

				function y(z) {
					if (t.status.disabled === true) {
						return
					}
					clearTimeout(t.timers.inactive);
					t.timers.inactive = setTimeout(function() {
						f(s).each(function() {
							u.unbind(this + ".qtip-inactive");
							t.elements.content.unbind(this + ".qtip-inactive")
						});
						t.hide(z)
					}, t.options.hide.delay)
				}
			} else {
				if (t.options.hide.fixed === true) {
					t.elements.tooltip.bind("mouseover.qtip", function() {
						if (t.status.disabled === true) {
							return
						}
						clearTimeout(t.timers.hide)
					})
				}
			}

			function x(z) {
				if (t.status.disabled === true) {
					return
				}
				if (t.options.hide.when.event == "inactive") {
					f(s).each(function() {
						u.bind(this + ".qtip-inactive", y);
						t.elements.content.bind(this + ".qtip-inactive", y)
					});
					y()
				}
				clearTimeout(t.timers.show);
				clearTimeout(t.timers.hide);
				t.timers.show = setTimeout(function() {
					t.show(z)
				}, t.options.show.delay)
			}

			function w(z) {
				if (t.status.disabled === true) {
					return
				}
				if (t.options.hide.fixed === true && t.options.hide.when.event.search(/mouse(out|leave)/i) !== -1 && f(z.relatedTarget).parents("div.qtip[qtip]").length > 0) {
					z.stopPropagation();
					z.preventDefault();
					clearTimeout(t.timers.hide);
					return false
				}
				clearTimeout(t.timers.show);
				clearTimeout(t.timers.hide);
				t.elements.tooltip.stop(true, true);
				t.timers.hide = setTimeout(function() {
					t.hide(z)
				}, t.options.hide.delay)
			}
			if ((t.options.show.when.target.add(t.options.hide.when.target).length === 1 && t.options.show.when.event == t.options.hide.when.event && t.options.hide.when.event !== "inactive") || t.options.hide.when.event == "unfocus") {
				t.cache.toggle = 0;
				v.bind(t.options.show.when.event + ".qtip", function(z) {
					if (t.cache.toggle == 0) {
						x(z)
					} else {
						w(z)
					}
				})
			} else {
				v.bind(t.options.show.when.event + ".qtip", x);
				if (t.options.hide.when.event !== "inactive") {
					u.bind(t.options.hide.when.event + ".qtip", w)
				}
			}
			if (t.options.position.type.search(/(fixed|absolute)/) !== -1) {
				t.elements.tooltip.bind("mouseover.qtip", t.focus)
			}
			if (t.options.position.target === "mouse" && t.options.position.type !== "static") {
				v.bind("mousemove.qtip", function(z) {
					t.cache.mouse = {
						x: z.pageX,
						y: z.pageY
					};
					if (t.status.disabled === false && t.options.position.adjust.mouse === true && t.options.position.type !== "static" && t.elements.tooltip.css("display") !== "none") {
						t.updatePosition(z)
					}
				})
			}
		}

		function o(u, v, A) {
			var z, s, x, y, t, w;
			z = this;
			if (A.corner == "center") {
				return v.position
			}
			s = f.extend({}, u);
			y = {
				x: false,
				y: false
			};
			t = {
				left: (s.left < f.fn.qtip.cache.screen.scroll.left),
				right: (s.left + A.dimensions.width + 2 >= f.fn.qtip.cache.screen.width + f.fn.qtip.cache.screen.scroll.left),
				top: (s.top < f.fn.qtip.cache.screen.scroll.top),
				bottom: (s.top + A.dimensions.height + 2 >= f.fn.qtip.cache.screen.height + f.fn.qtip.cache.screen.scroll.top)
			};
			x = {
				left: (t.left && (A.corner.search(/right/i) != -1 || (A.corner.search(/right/i) == -1 && !t.right))),
				right: (t.right && (A.corner.search(/left/i) != -1 || (A.corner.search(/left/i) == -1 && !t.left))),
				top: (t.top && A.corner.search(/top/i) == -1),
				bottom: (t.bottom && A.corner.search(/bottom/i) == -1)
			};
			if (x.left) {
				if (z.options.position.target !== "mouse") {
					s.left = v.position.left + v.dimensions.width
				} else {
					s.left = z.cache.mouse.x
				}
				y.x = "Left"
			} else {
				if (x.right) {
					if (z.options.position.target !== "mouse") {
						s.left = v.position.left - A.dimensions.width
					} else {
						s.left = z.cache.mouse.x - A.dimensions.width
					}
					y.x = "Right"
				}
			}
			if (x.top) {
				if (z.options.position.target !== "mouse") {
					s.top = v.position.top + v.dimensions.height
				} else {
					s.top = z.cache.mouse.y
				}
				y.y = "top"
			} else {
				if (x.bottom) {
					if (z.options.position.target !== "mouse") {
						s.top = v.position.top - A.dimensions.height
					} else {
						s.top = z.cache.mouse.y - A.dimensions.height
					}
					y.y = "bottom"
				}
			}
			if (s.left < 0) {
				s.left = u.left;
				y.x = false
			}
			if (s.top < 0) {
				s.top = u.top;
				y.y = false
			}
			if (z.options.style.tip.corner !== false) {
				s.corner = new String(A.corner);
				if (y.x !== false) {
					s.corner = s.corner.replace(/Left|Right|Middle/, y.x)
				}
				if (y.y !== false) {
					s.corner = s.corner.replace(/top|bottom/, y.y)
				}
				if (s.corner !== z.elements.tip.attr("rel")) {
					e.call(z, s.corner)
				}
			}
			return s
		}

		function q(u, t) {
			var v, s;
			v = f.extend(true, {}, u);
			for (s in v) {
				if (t === true && s.search(/(tip|classes)/i) !== -1) {
					delete v[s]
				} else {
					if (!t && s.search(/(width|border|tip|title|classes|user)/i) !== -1) {
						delete v[s]
					}
				}
			}
			return v
		}

		function c(s) {
			if (typeof s.tip !== "object") {
				s.tip = {
					corner: s.tip
				}
			}
			if (typeof s.tip.size !== "object") {
				s.tip.size = {
					width: s.tip.size,
					height: s.tip.size
				}
			}
			if (typeof s.border !== "object") {
				s.border = {
					width: s.border
				}
			}
			if (typeof s.width !== "object") {
				s.width = {
					value: s.width
				}
			}
			if (typeof s.width.max == "string") {
				s.width.max = parseInt(s.width.max.replace(/([0-9]+)/i, "$1"))
			}
			if (typeof s.width.min == "string") {
				s.width.min = parseInt(s.width.min.replace(/([0-9]+)/i, "$1"))
			}
			if (typeof s.tip.size.x == "number") {
				s.tip.size.width = s.tip.size.x;
				delete s.tip.size.x
			}
			if (typeof s.tip.size.y == "number") {
				s.tip.size.height = s.tip.size.y;
				delete s.tip.size.y
			}
			return s
		}

		function a() {
			var s, t, u, x, v, w;
			s = this;
			u = [true, {}];
			for (t = 0; t < arguments.length; t++) {
				u.push(arguments[t])
			}
			x = [f.extend.apply(f, u)];
			while (typeof x[0].name == "string") {
				x.unshift(c(f.fn.qtip.styles[x[0].name]))
			}
			x.unshift(true, {
				classes: {
					tooltip: "qtip-" + (arguments[0].name || "defaults")
				}
			}, f.fn.qtip.styles.defaults);
			v = f.extend.apply(f, x);
			w = (f.browser && f.browser.msie) ? 1 : 0;
			v.tip.size.width += w;
			v.tip.size.height += w;
			if (v.tip.size.width % 2 > 0) {
				v.tip.size.width += 1
			}
			if (v.tip.size.height % 2 > 0) {
				v.tip.size.height += 1
			}
			if (v.tip.corner === true) {
				v.tip.corner = (s.options.position.corner.tooltip === "center") ? false : s.options.position.corner.tooltip
			}
			return v
		}

		function b(v, u, t) {
			var s = {
				bottomRight: [
					[0, 0],
					[u, t],
					[u, 0]
				],
				bottomLeft: [
					[0, 0],
					[u, 0],
					[0, t]
				],
				topRight: [
					[0, t],
					[u, 0],
					[u, t]
				],
				topLeft: [
					[0, 0],
					[0, t],
					[u, t]
				],
				topMiddle: [
					[0, t],
					[u / 2, 0],
					[u, t]
				],
				bottomMiddle: [
					[0, 0],
					[u, 0],
					[u / 2, t]
				],
				rightMiddle: [
					[0, 0],
					[u, t / 2],
					[0, t]
				],
				leftMiddle: [
					[u, 0],
					[u, t],
					[0, t / 2]
				]
			};
			s.leftTop = s.bottomRight;
			s.rightTop = s.bottomLeft;
			s.leftBottom = s.topRight;
			s.rightBottom = s.topLeft;
			return s[v]
		}

		function g(s) {
			var t;
			if (f("<canvas>").get(0).getContext) {
				t = {
					topLeft: [s, s],
					topRight: [0, s],
					bottomLeft: [s, 0],
					bottomRight: [0, 0]
				}
			} else {
				if (f.browser && f.browser.msie) {
					t = {
						topLeft: [-90, 90, 0],
						topRight: [-90, 90, -s],
						bottomLeft: [90, 270, 0],
						bottomRight: [90, 270, -s]
					}
				}
			}
			return t
		}

		function k() {
			var s, t, u;
			s = this;
			u = s.getDimensions();
			t = '<iframe class="qtip-bgiframe" frameborder="0" tabindex="-1" src="javascript:false" style="display:block; position:absolute; z-index:-1; filter:alpha(opacity=\'0\'); border: 1px solid red; height:' + u.height + "px; width:" + u.width + 'px" />';
			s.elements.bgiframe = s.elements.wrapper.prepend(t).children(".qtip-bgiframe:first")
		}
		f(document).ready(function() {
			f.fn.qtip.cache = {
				screen: {
					scroll: {
						left: f(window).scrollLeft(),
						top: f(window).scrollTop()
					},
					width: f(window).width(),
					height: f(window).height()
				}
			};
			var s;
			f(window).bind("resize scroll", function(t) {
				clearTimeout(s);
				s = setTimeout(function() {
					if (t.type === "scroll") {
						f.fn.qtip.cache.screen.scroll = {
							left: f(window).scrollLeft(),
							top: f(window).scrollTop()
						}
					} else {
						f.fn.qtip.cache.screen.width = f(window).width();
						f.fn.qtip.cache.screen.height = f(window).height()
					}
					for (i = 0; i < f.fn.qtip.interfaces.length; i++) {
						var u = f.fn.qtip.interfaces[i];
						if (u.status.rendered === true && (u.options.position.type !== "static" || u.options.position.adjust.scroll && t.type === "scroll" || u.options.position.adjust.resize && t.type === "resize")) {
							u.updatePosition(t, true)
						}
					}
				}, 100)
			});
			f(document).bind("mousedown.qtip", function(t) {
				if (f(t.target).parents("div.qtip").length === 0) {
					f(".qtip[unfocus]").each(function() {
						var u = f(this).qtip("api");
						if (f(this).is(":visible") && !u.status.disabled && f(t.target).add(u.elements.target).length > 1) {
							u.hide(t)
						}
					})
				}
			})
		});
		f.fn.qtip.interfaces = [];
		f.fn.qtip.log = {
			error: function() {
				return this
			}
		};
		f.fn.qtip.constants = {};
		f.fn.qtip.defaults = {
			content: {
				prerender: false,
				text: false,
				url: false,
				data: null,
				title: {
					text: false,
					button: false
				}
			},
			position: {
				target: false,
				corner: {
					target: "bottomRight",
					tooltip: "topLeft"
				},
				adjust: {
					x: 0,
					y: 0,
					mouse: true,
					screen: false,
					scroll: true,
					resize: true
				},
				type: "absolute",
				container: false
			},
			show: {
				when: {
					target: false,
					event: "mouseover"
				},
				effect: {
					type: "fade",
					length: 100
				},
				delay: 140,
				solo: false,
				ready: false
			},
			hide: {
				when: {
					target: false,
					event: "mouseout"
				},
				effect: {
					type: "fade",
					length: 100
				},
				delay: 0,
				fixed: false
			},
			api: {
				beforeRender: function() {},
				onRender: function() {},
				beforePositionUpdate: function() {},
				onPositionUpdate: function() {},
				beforeShow: function() {},
				onShow: function() {},
				beforeHide: function() {},
				onHide: function() {},
				beforeContentUpdate: function() {},
				onContentUpdate: function() {},
				beforeContentLoad: function() {},
				onContentLoad: function() {},
				beforeTitleUpdate: function() {},
				onTitleUpdate: function() {},
				beforeDestroy: function() {},
				onDestroy: function() {},
				beforeFocus: function() {},
				onFocus: function() {}
			}
		};
		f.fn.qtip.styles = {
			defaults: {
				background: "white",
				color: "#111",
				overflow: "hidden",
				textAlign: "left",
				width: {
					min: 0,
					max: 250
				},
				padding: "5px 9px",
				border: {
					width: 1,
					radius: 0,
					color: "#d3d3d3"
				},
				tip: {
					corner: false,
					color: false,
					size: {
						width: 13,
						height: 13
					},
					opacity: 1
				},
				title: {
					background: "#e1e1e1",
					fontWeight: "bold",
					padding: "7px 12px"
				},
				button: {
					cursor: "pointer"
				},
				classes: {
					target: "",
					tip: "qtip-tip",
					title: "qtip-title",
					button: "qtip-button",
					content: "qtip-content",
					active: "qtip-active"
				}
			},
			cream: {
				border: {
					width: 3,
					radius: 0,
					color: "#F9E98E"
				},
				title: {
					background: "#F0DE7D",
					color: "#A27D35"
				},
				background: "#FBF7AA",
				color: "#A27D35",
				classes: {
					tooltip: "qtip-cream"
				}
			},
			light: {
				border: {
					width: 3,
					radius: 0,
					color: "#E2E2E2"
				},
				title: {
					background: "#f1f1f1",
					color: "#454545"
				},
				background: "white",
				color: "#454545",
				classes: {
					tooltip: "qtip-light"
				}
			},
			dark: {
				border: {
					width: 3,
					radius: 0,
					color: "#303030"
				},
				title: {
					background: "#404040",
					color: "#f3f3f3"
				},
				background: "#505050",
				color: "#f3f3f3",
				classes: {
					tooltip: "qtip-dark"
				}
			},
			red: {
				border: {
					width: 3,
					radius: 0,
					color: "#CE6F6F"
				},
				title: {
					background: "#f28279",
					color: "#9C2F2F"
				},
				background: "#F79992",
				color: "#9C2F2F",
				classes: {
					tooltip: "qtip-red"
				}
			},
			green: {
				border: {
					width: 3,
					radius: 0,
					color: "#A9DB66"
				},
				title: {
					background: "#b9db8c",
					color: "#58792E"
				},
				background: "#CDE6AC",
				color: "#58792E",
				classes: {
					tooltip: "qtip-green"
				}
			},
			blue: {
				border: {
					width: 3,
					radius: 0,
					color: "#ADD9ED"
				},
				title: {
					background: "#D0E9F5",
					color: "#5E99BD"
				},
				background: "#E5F6FE",
				color: "#4D9FBF",
				classes: {
					tooltip: "qtip-blue"
				}
			}
		}
	})(jQuery);

	/**
	 * Excel
	 */
	var n = String.fromCharCode,
		p;
	a: {
		try {
			document.createElement("$")
		} catch (q) {
			p = q;
			break a
		}
		p = void 0
	}
	window.btoa || (window.btoa = function(b) {
		for (var g, c, f, h, e, a, d = 0, r = b.length, s = Math.max, l = ""; d < r;) {
			g = b.charCodeAt(d++) || 0;
			c = b.charCodeAt(d++) || 0;
			a = b.charCodeAt(d++) || 0;
			if (255 < s(g, c, a)) throw p;
			f = g >> 2 & 63;
			g = (g & 3) << 4 | c >> 4 & 15;
			h = (c & 15) << 2 | a >> 6 & 3;
			e = a & 63;
			c ? a || (e = 64) : h = e = 64;
			l += "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(f) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(g) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(h) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(e)
		}
		return l
	});
	window.atob || (window.atob = function(b) {
		b = b.replace(/=+$/, "");
		var g, c, f, h, e = 0,
			a = b.length,
			d = [];
		if (1 === a % 4) throw p;
		for (; e < a;) g = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(b.charAt(e++)), c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(b.charAt(e++)), f = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(b.charAt(e++)), h = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(b.charAt(e++)), g = (g & 63) << 2 | c >> 4 & 3, c = (c & 15) << 4 | f >> 2 & 15, f = (f & 3) << 6 | h & 63, d.push(n(g)), c && d.push(n(c)), f && d.push(n(f));
		return d.join("")
	});
	ExcellentExport = function() {
		function b(e, a) {
			return e.replace(RegExp("{(\\w+)}", "g"), function(d, e) {
				return a[e]
			})
		}
		var g = {
				excel: "data:application/vnd.ms-excel;base64,",
				csv: "data:application/csv;base64,"
			},
			c = {
				excel: '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">\x3c!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--\x3e</head><body><table>{table}</table></body></html>'
			},
			f = ",",
			h = "\r\n";
		return {
			excel: function(e, a, d) {
				a = a.nodeType ? a : document.getElementById(a);
				var f = g.excel;
				a = b(c.excel, {
					a: d || "Worksheet",
					table: a.innerHTML
				});
				a = window.btoa(window.unescape(encodeURIComponent(a)));
				e.href = f + a;
				return !0
			},
			csv: function(e, a, d, b) {
				void 0 !== d && d && (f = d);
				void 0 !== b && b && (h = b);
				a = a.nodeType ? a : document.getElementById(a);
				var c = "",
					l, k;
				for (d = 0; d < a.rows.length; d++) {
					l = a.rows[d];
					for (b = 0; b < l.cells.length; b++) {
						k = l.cells[b];
						var c = c + (b ? f : ""),
							m = k.textContent.trim();
						k = m;
						var t = -1 !== m.indexOf(f) || -1 !== m.indexOf("\r") || -1 !== m.indexOf("\n");
						(m = -1 !== m.indexOf('"')) && (k = k.replace(/"/g, '""'));
						if (t || m) k = '"' + k + '"';
						c += k
					}
					c += h
				}
				a = g.csv + window.btoa(window.unescape(encodeURIComponent(c)));
				e.href = a;
				return !0
			}
		}
	}();
} catch (e) {
	console.log(e)
}
/*
	Masked Input plugin for jQuery
	Copyright (c) 2007-2011 Josh Bush (digitalbush.com)
	Licensed under the MIT license (http://digitalbush.com/projects/masked-input-plugin/#license)
	Version: 1.3
*/
try {
	(function(a) {
		var b = (a.browser && a.browser.msie ? "paste" : "input") + ".mask_input",
			c = window.orientation != undefined;
		a.mask_input = {
			definitions: {
				9: "[0-9]",
				a: "[A-Za-z]",
				"*": "[A-Za-z0-9]"
			},
			dataName: "rawmask_inputFn"
		}, a.fn.extend({
			caret: function(a, b) {
				if (this.length != 0) {
					if (typeof a == "number") {
						b = typeof b == "number" ? b : a;
						return this.each(function() {
							if (this.setSelectionRange) this.setSelectionRange(a, b);
							else if (this.createTextRange) {
								var c = this.createTextRange();
								c.collapse(!0), c.moveEnd("character", b), c.moveStart("character", a), c.select()
							}
						})
					}
					if (this[0].setSelectionRange) a = this[0].selectionStart, b = this[0].selectionEnd;
					else if (document.selection && document.selection.createRange) {
						var c = document.selection.createRange();
						a = 0 - c.duplicate().moveStart("character", -1e5), b = a + c.text.length
					}
					return {
						begin: a,
						end: b
					}
				}
			},
			unmask_input: function() {
				return this.trigger("unmask_input")
			},
			mask_input: function(d, e) {
				if (!d && this.length > 0) {
					var f = a(this[0]);
					return f.data(a.mask_input.dataName)()
				}
				e = a.extend({
					placeholder: "_",
					completed: null
				}, e);
				var g = a.mask_input.definitions,
					h = [],
					i = d.length,
					j = null,
					k = d.length;
				a.each(d.split(""), function(a, b) {
					b == "?" ? (k--, i = a) : g[b] ? (h.push(new RegExp(g[b])), j == null && (j = h.length - 1)) : h.push(null)
				});
				return this.trigger("unmask_input").each(function() {
					function v(a) {
						var b = f.val(),
							c = -1;
						for (var d = 0, g = 0; d < k; d++)
							if (h[d]) {
								l[d] = e.placeholder;
								while (g++ < b.length) {
									var m = b.charAt(g - 1);
									if (h[d].test(m)) {
										l[d] = m, c = d;
										break
									}
								}
								if (g > b.length) break
							} else l[d] == b.charAt(g) && d != i && (g++, c = d);
						if (!a && c + 1 < i) f.val(""), t(0, k);
						else if (a || c + 1 >= i) u(), a || f.val(f.val().substring(0, c + 1));
						return i ? d : j
					}

					function u() {
						return f.val(l.join("")).val()
					}

					function t(a, b) {
						for (var c = a; c < b && c < k; c++) h[c] && (l[c] = e.placeholder)
					}

					function s(a) {
						var b = a.which,
							c = f.caret();
						if (a.ctrlKey || a.altKey || a.metaKey || b < 32) return !0;
						if (b) {
							c.end - c.begin != 0 && (t(c.begin, c.end), p(c.begin, c.end - 1));
							var d = n(c.begin - 1);
							if (d < k) {
								var g = String.fromCharCode(b);
								if (h[d].test(g)) {
									q(d), l[d] = g, u();
									var i = n(d);
									f.caret(i), e.completed && i >= k && e.completed.call(f)
								}
							}
							return !1
						}
					}

					function r(a) {
						var b = a.which;
						if (b == 8 || b == 46 || c && b == 127) {
							var d = f.caret(),
								e = d.begin,
								g = d.end;
							g - e == 0 && (e = b != 46 ? o(e) : g = n(e - 1), g = b == 46 ? n(g) : g), t(e, g), p(e, g - 1);
							return !1
						}
						if (b == 27) {
							f.val(m), f.caret(0, v());
							return !1
						}
					}

					function q(a) {
						for (var b = a, c = e.placeholder; b < k; b++)
							if (h[b]) {
								var d = n(b),
									f = l[b];
								l[b] = c;
								if (d < k && h[d].test(f)) c = f;
								else break
							}
					}

					function p(a, b) {
						if (!(a < 0)) {
							for (var c = a, d = n(b); c < k; c++)
								if (h[c]) {
									if (d < k && h[c].test(l[d])) l[c] = l[d], l[d] = e.placeholder;
									else break;
									d = n(d)
								}
							u(), f.caret(Math.max(j, a))
						}
					}

					function o(a) {
						while (--a >= 0 && !h[a]);
						return a
					}

					function n(a) {
						while (++a <= k && !h[a]);
						return a
					}
					var f = a(this),
						l = a.map(d.split(""), function(a, b) {
							if (a != "?") return g[a] ? e.placeholder : a
						}),
						m = f.val();
					f.data(a.mask_input.dataName, function() {
						return a.map(l, function(a, b) {
							return h[b] && a != e.placeholder ? a : null
						}).join("")
					}), f.attr("readonly") || f.one("unmask_input", function() {
						f.unbind(".mask_input").removeData(a.mask_input.dataName)
					}).bind("focus.mask_input", function() {
						m = f.val();
						var b = v();
						u();
						var c = function() {
							b == d.length ? f.caret(0, b) : f.caret(b)
						};
						(a.browser.msie ? c : function() {
							setTimeout(c, 0)
						})()
					}).bind("blur.mask_input", function() {
						v(), f.val() != m && f.change()
					}).bind("keydown.mask_input", r).bind("keypress.mask_input", s).bind(b, function() {
						setTimeout(function() {
							f.caret(v(!0))
						}, 0)
					}), v()
				})
			}
		})
	})(jQuery);
} catch (e) {
	console.log(e);
}
if (typeof console != "undefined" && typeof console.debug != "undefined") {
	console.log("Debug Mode");
} else {
	var console = {
		log: function() {},
		debug: function() {}
	}
}
if (typeof liftAjax == 'undefined') { //new session
	liftAjax = {
		lift_successRegisterGC: function() {}
	};
	liftAjax.lift_sessionLost = function() {
		location.reload();
	}
}
//Funcion utils
function gup(name) {
	var searchString = window.location.search.substring(1);
	var variableArray = searchString.split('&');
	var results = [];
	var result = "";
	for (var i = 0; i < variableArray.length; i++) {
		var keyValuePair = variableArray[i].split('=');
		if (keyValuePair[0] == name) {
			results.push(decodeURIComponent(keyValuePair[1]).replace("+", " "));
		}
	}
	if (results.length == 1) {
		return results[0];
	} else if (results.length > 1) {
		result = results;
	}
	return result;
}

//Lift to Boostrap...	
var messages = function() {
	$("#lift__noticesContainer___notice li").addClass("alert-message sucess");
	$("#lift__noticesContainer___error li").addClass("alert-message error");
	$("#lift__noticesContainer___warning li").addClass("alert-message warning");
};
var loginInformationProcess = function() {
	$('.login_form').each(function() {
		$('[name="user"]', this).localStorageField();
		$('[name="company"]', this).localStorageField();
		$('[name="password"]', this).focus();
	});
};
var markMessageAsRead = function(id) {
	$.get("/messages/read/" + id, function(resut) {
		$("#message_" + id).remove();
		if ($("#messages_modal .modal-body").html() == "")
			$("#messages_modal").modal("hide");
	});
};
var markMessageAsHide = function(id) {
	$.get("/messages/hide/" + id, function(resut) {
		$("#message_" + id).remove();
		if ($("#messages_modal .modal-body").html() == "")
			$("#messages_modal").modal("hide");
	});
};
var showMessages = function(messages) {
	var SYSTEM = 2;
	var messages_str = "";
	for (var i = messages.length - 1; i >= 0; i--) {
		var sended = new Date(messages[i].sended);
		var style = '';
		if (messages[i].messageType == SYSTEM) {
			style = "background: yellowgreen;";
		}
		messages_str += '<div style="' + style + 
		'" class="alert-message block-message error fade in" id="message_' + 
		messages[i].id + '" data-alert="alert"><a class="close" href="#">×</a><b>De : </b>' + 
		messages[i].of + '<br/><b>Título :' + messages[i].subject + '</b> <br/><p>' + 
		'</p><br/><p> <b>Enviado : </b>' + sended.getTextWhen() + 
		' às ' + sended.getHourBr() + '</p><div class="alert-actions"><a class="btn danger small" onclick="markMessageAsRead(' + messages[i].id + 
		')" href="#">Excluir</a> <a class="btn small" onclick="markMessageAsHide(' + messages[i].id + 
		')" href="#">Ler Depois!</a><br/><br/></div>' +
		messages[i].message + '</p><br/><p> <b>Enviado : </b>' + sended.getTextWhen() + 
		' às ' + sended.getHourBr() + '</p><div class="alert-actions"><a class="btn danger small" onclick="markMessageAsRead(' + messages[i].id + 
		')" href="#">Excluir</a> <a class="btn small" onclick="markMessageAsHide(' + messages[i].id + 
		')" href="#">Ler Depois!</a></div></div>';
	};
	if (messages_str != "") {
		$("#messages_modal .modal-body").html(messages_str);
		$("#messages_modal").modal({
			"backdrop": true,
			'keyboard': true,
			'show': true
		});
	}
}
var showHidedMessages = function() {
	$.get("/messages/hided", function(resut) {
		eval("messages = " + resut);
		if (messages.length > 0)
			showMessages(messages);
		else
			alert("Não existem mensagens marcadas como <Ler Depois>!")
	});
}
var showUnredMessage = function() {
	$.get("/messages", function(resut) {
		eval("messages = " + resut);
		showMessages(messages);
	});
}
var processMessages = function() {
	if (typeof logged != "undefined" && logged) {
		$("#menu_messages_hided").click(function() {
			showHidedMessages();
		});

		$("#menu_messages").click(function() {
			$("#send_messages_modal").modal({
				"backdrop": true,
				'keyboard': true,
				'show': true
			});
		});
		$(".map_key").click(function() {
			$("#key_map_model").modal({
				"backdrop": true,
				'keyboard': true,
				'show': true
			});
		});
		$("#message_send").click(function() {
			users = $("#message_users").val();
			if (typeof users === 'object' && users !== null) {
				users = users.join(",");
			} else {
				users = ""
			}
			$.post("/messages", {
				"subject": $("#message_title").val(),
				"users": users,
				"message": $("#message_message").val(),
				"group": $("#message_groups").val(),
				"expirationdate": $("#expirationdate").val()
			}, function(r) {
				alert("Mensagem enviada com sucesso!");
				$("#send_messages_modal").modal("hide");
			});
		});
		DataManager.getUserGroups(function(results) {
			groups = "<option value=''>Selecione grupo de profissionais</option>";
			for (var i in results) {
				obj = results[i];
				groups += "<option value='" + obj.id + "'>" + obj.name + "</option>";
			}
			$("#message_groups").html(groups);
		});

		DataManager.getAllUsers(function(userObj) {
			user_str = "";
			user_str += "<option value='SELECT_ALL'>Todos</option>";
			for (var i in userObj) {
				user_str += "<option value='" + userObj[i].id + "'>" + userObj[i].name + "</option>";
			}
			$("#message_users").html(user_str);
		});
		showUnredMessage();
		var $products = $(".product_select");
		if ($products.productSearch) {
			$products.productSearch({
				createName: false,
				iconElement: ".add-on",
				userThuch: true
			})
		}
	}
};

var toGerirme = function() {
	I18nManager.changeLang("gerirme");
	$('title').html('gerir-me ' + $('title').html());
	$('.system-name').html('gerir-me');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_gerirme.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_gerirme.png');
	$('.body').css('background-image', 'url("/images/capa_gerirme.jpg")');
};
var toEgrex = function() {
	I18nManager.changeLang("egrex");
	$('title').html('e-grex ' + $('title').html());
	$('.system-name').html('e-grex');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_egrex.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_egrex.png');
	$('.body').css('background-image', 'url("/images/capa_egrex.jpg")');
};
var toEsmile = function() {
	I18nManager.changeLang("esmile");
	$('title').html('e-smile ' + $('title').html());
	$('.system-name').html('e-smile');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_esmile.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_esmile.png');
	$('.body').css('background-image', 'url("/images/capa_esmile.jpg")');
	$("a[href='/pricing']").attr('href', '/pricing_ephysio');
};
var toEdoctus = function() {
	I18nManager.changeLang("edoctus");
	$('title').html('e-doctus ' + $('title').html());
	$('.system-name').html('e-doctus');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_edoctus.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_edoctus.png');
	$('.body').css('background-image', 'url("/images/capa_edoctus.jpg")');
	$("a[href='/pricing']").attr('href', '/pricing_ephysio');
};
var toEphysio = function() {
	I18nManager.changeLang("ephysio");
	$('title').html('e-physio ' + $('title').html());
	$('.system-name').html('e-physio');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_ephysio.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_ephysio.png');
	$('.body').css('background-image', 'url("/images/capa_ephysio.jpg")');
	$("a[href='/pricing']").attr('href', '/pricing_ephysio');
};
var toEbellepet = function() {
	I18nManager.changeLang("ebellepet");
	$('title').html('e-bellepet ' + $('title').html());
	$('.system-name').html('e-bellepet');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_ebellepet.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_ebellepet.png');
	$("img[src='/images/favicon.ico']").attr('src', '/images/logo_ftr_ebellepet.ico');
	$('.body').css('background-image', 'url("/images/capa_ebellepet.jpg")');
	$("a[href='/pricing']").attr('href', '/pricing_ephysio');
};
var toEbelle = function() {
	I18nManager.changeLang("ebelle");
	$('title').html('e-belle ' + $('title').html());
	$('.system-name').html('e-belle');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_ebelle.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_ebelle.png');
	$("img[src='/images/favicon.ico']").attr('src', '/images/logo_ftr_ebelle.ico');
	//$('body').css('background-image','url("/images/capa_ebelle.jpg")');
};
var validateEmail = function(window) {
	var currentDate = (new Date().getDay());
	var lastDay = localStorage.getItem('lasDayEmail');
	if (lastDay != currentDate) {
		$.get('/security/emailRulesCheck', function(a) {
			var data;
			eval("data=" + a);
			if (data != "1") {
				localStorage.setItem('lasDayEmail', currentDate);
				if (!data.email) {
					//					alert("A partir de março/2015 só será possível efetuar login através de e-mail, solicite ao administrador que cadastre o seu.");
				} else {
					//					alert("A partir de março/2015 só será possível efetuar login através do e-mail " + data.email);
				}
			}
		});
	}
};

var I18nManager = {
	lang: 'ebelle',
	words: Languages,
	changeLang: function(lang) {
		I18nManager.lang = lang;
		I18nManager.applyLang();
	},
	i18n: function(word, words, lang) {
		var wordTrl = false,
			langWords = false;
		if (words[lang]) {
			langWords = words[lang];
		} else if (words['default']) {
			langWords = words['default'];
		}
		if (langWords[word]) {
			wordTrl = langWords[word];
		}
		return wordTrl || word;
	},
	applyLang: function(context) {
		$("[data-i18n]", context || document).each(function() {
			$(this).html(I18nManager.i18n($(this).attr('data-i18n'), I18nManager.words, I18nManager.lang));
		});
	}
};

