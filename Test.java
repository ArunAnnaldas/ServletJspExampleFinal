def highlight_differences(pdf_path, differences, output_path):
    """
    Highlights differences in the PDF and saves it.

    Args:
        pdf_path (str): Original PDF.
        differences (dict): Differences detected.
        output_path (str): Path to save the highlighted PDF.
    """
    doc = fitz.open(pdf_path)
    for page_num, diffs in differences.items():
        page = doc.load_page(page_num - 1)
        for diff in diffs:
            text_instances = page.search_for(diff.strip())  # Strip spaces
            if not text_instances:
                print(f"⚠️ No match found for: {diff} on Page {page_num}")
            for inst in text_instances:
                highlight = page.add_highlight_annot(inst)
                highlight.update()
    doc.save(output_path)
    print(f"✅ Differences highlighted in: {output_path}")
